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
        format (partial re-find #"\S+")]
    (->> (send-heroku-command "apps")
         (filter app?)
         (filter match?)
         (map format))))

(defn get-response [command apps]
  (let [get-single-response #(apply send-heroku-command (merge command "--app" %))
        response (pmap get-single-response apps)]
    (map vector apps response)))
