(ns fukusu.output
  (:require [fukusu.core :as core]
            [clojure.string :as string]))

(defn- print-results [results]
  (doall
   (for [[name result] results]
     (println (format "%-40s %s" name result)))))

(defn list-apps [app-regex]
  (doall
   (map println (core/get-app-names app-regex)))
  (System/exit 0))

(defn list-ruby [app-regex]
  (let [app-names (core/get-app-names app-regex)
        command "ruby -v"
        split-by-space #(string/split % #"\s")
        formatter (comp second split-by-space last)]
    (print-results (core/apply-command-and-format command app-names formatter))
    (System/exit 0)))

(defn list-gem [gem-name app-regex]
  (let [app-names (core/get-app-names app-regex)
        command (str "bundle show " gem-name)
        split-by-dash #(string/split % #"-")
        formatter (comp second split-by-dash last)]
    (print-results (core/apply-command-and-format command app-names formatter))
    (System/exit 0)))
