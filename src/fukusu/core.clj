(ns fukusu.core
  (:require
   [clojure.java.shell :refer [sh]]
   [clojure.string :as string]))

(defn send-heroku-command [& args]
  (let [output-string (:out (apply sh "heroku" args))]
    (string/split-lines output-string)))

(defn get-app-names [app-regex]
  (let [app? (fn [line] (not (string/starts-with? line "===")))
        match? (partial re-find app-regex)
        format (fn [line] (first (string/split line #"\s")))]
    (->> (send-heroku-command "apps")
         (filter app?)
         (filter match?)
         (map format))))

(defn apply-command [command apps]
  (pmap (fn [name] {:name name
                    :output (send-heroku-command "run" command "--app" name)}) apps))

(defn apply-command-and-format [command apps formatter]
  (let [result (apply-command command apps)]
    (map #(update % :output formatter) result)))
