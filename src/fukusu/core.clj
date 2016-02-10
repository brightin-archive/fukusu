(ns fukusu.core
  (:require
   [clojure.java.shell :refer [sh]]
   [clojure.string :as string]))

(defn app? [line]
  (not (string/starts-with? line "===")))

(defn format-app-name [app-name]
  (first (string/split app-name #"\s")))

(defn send-heroku-command [& args]
  (let [output-string (:out (apply sh "heroku" args))]
    (string/split-lines output-string)))

(defn get-app-names [app-regex]
  (let [match? (partial re-find app-regex)]
    (->> (send-heroku-command "apps")
         (filter app?)
         (filter match?)
         (map format-app-name))))

(defn apply-command [command apps]
  (pmap (fn [name] {:name name
                    :output (send-heroku-command "run" command "--app" name)}) apps))

(defn apply-command-and-format [command apps formatter]
  (let [result (apply-command command apps)]
    (map #(update % :output formatter) result)))
