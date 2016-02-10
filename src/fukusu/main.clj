(ns fukusu.main
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]
            [fukusu.output :as output]))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn error-msg [errors]
  (str "The following errors occurred while parsing your command:\n\n"
       (string/join \newline errors)))

(defn usage [options-summary]
  (->> ["Fukusu: run Heroku commands mulitple apps"
        ""
        "Usage: fukusu action [options]"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        "  list:apps              # List all apps"
        "  list:ruby              # List Ruby version for apps"
        "  list:gem GEM_NAME      # List Gem versions for apps"]
       (string/join \newline)))

(defn config []
  (let [homedir (System/getenv "HOME")
        config-path (str homedir "/.fukusu-config.edn")
        config-file (io/file config-path)]
    (if (.exists config-file)
      (clojure.edn/read-string (slurp config-file))
      {:default-regex "."})))

(def cli-options
  [["-a" "--apps REGEX" "Regex to limit action to specific apps."
    :default (re-pattern (:default-regex (config)))
    :parse-fn re-pattern]
   ["-h" "--help"]])

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)
        app-regex (:apps options)]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors)))
    (case (first arguments)
      "list:apps" (output/list-apps app-regex)
      "list:ruby" (output/list-ruby app-regex)
      "list:gem" (output/list-gem (second arguments) app-regex)
      (exit 1 (usage summary)))))
