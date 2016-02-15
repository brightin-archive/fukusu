(ns fukusu.commands
  (:require [fukusu.core :as core]
            [fukusu.util :as util]))

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
  (let [command ["run" "ruby -v"]
        formatter (comp second util/split-by-space last)]
    (print-response formatter (core/get-response command app-names))
    (System/exit 0)))

(defn list-gem
  "List gem versions for apps"
  [app-names [gem-name]]
  (let [command ["run" (str "bundle show " gem-name)]
        formatter (comp second util/split-by-dash last)]
    (print-response formatter (core/get-response command app-names))
    (System/exit 0)))

(defn list-db
  "List the Heroku Postgres database plan"
  [app-names [_]]
  (let [command ["pg:info"]
        formatter (comp second (partial re-find #"Plan:\s+(.+)") str second)]
    (print-response formatter (core/get-response command app-names))
    (System/exit 0)))

(def all
  {"list:apps" #'list-apps
   "list:ruby" #'list-ruby
   "list:gem" #'list-gem
   "list:db" #'list-db})

(def usage
  (->>
   (for [[name fn] all]
     (format "  %-12s # %s" name (:doc (meta fn))))
   (apply util/long-str)))
