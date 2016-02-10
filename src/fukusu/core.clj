(ns fukusu.core
  (:require
   [clojure.java.shell :refer [sh]]
   [clojure.string :as string]))

(defn send-heroku-command [& args]
  (let [output-string (:out (apply sh "heroku" args))]
    (string/split-lines output-string)))

(defn get-app-names [app-regex]
  (let [app? #(not (string/starts-with? % "==="))
        match? (partial re-find app-regex)
        format #(first (string/split % #"\s"))]
    (->> (send-heroku-command "apps")
         (filter app?)
         (filter match?)
         (map format))))

(defn apply-command [command apps]
  (pmap #(send-heroku-command "run" command "--app" %) apps))

(defn apply-command-and-format [command apps formatter]
  (let [results (map formatter (apply-command command apps))]
    (zipmap apps results)))
