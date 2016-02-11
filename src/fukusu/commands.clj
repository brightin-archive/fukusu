(ns fukusu.commands
  (:require [fukusu.core :as core]
            [clojure.string :as string]))

(defn print-response [formatter response]
  (doall
   (for [[name r] response]
     (println (format "%-40s %s" name (formatter r))))))

(defn list-apps
  "List all apps"
  [app-names _]
  (doall
   (map println app-names))
  (System/exit 0))

(defn list-ruby
  "List Ruby versions for apps"
  [app-names _]
  (let [command "ruby -v"
        split-by-space #(string/split % #"\s")
        formatter (comp second split-by-space last)]
    (print-response formatter (core/get-response command app-names))
    (System/exit 0)))

(defn list-gem
  "List gem versions for apps"
  [app-names [gem-name]]
  (let [command (str "bundle show " gem-name)
        split-by-dash #(string/split % #"-")
        formatter (comp second split-by-dash last)]
    (print-response formatter (core/get-response command app-names))
    (System/exit 0)))

(def all
  {"list:apps" #'list-apps
   "list:ruby" #'list-ruby
   "list:gem" #'list-gem})

(def usage
  (->>
   (for [[name fn] all]
     (format "  %-15s # %s" name (:doc (meta fn))))
   (string/join \newline)))
