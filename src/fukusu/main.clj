(ns fukusu.main
  (:gen-class)
  (:require [clojure.java.io :as io]
            [clojure.string :as string]
            [clojure.tools.cli :refer [parse-opts]]
            [fukusu.commands :as commands]
            [fukusu.core :as core]))

(defn exit [status msg]
  (println msg)
  (System/exit status))

(defn usage [options-summary]
  (->> ["Fukusu: run Heroku commands against multiple apps"
        ""
        "Usage: fukusu action [options]"
        ""
        "Options:"
        options-summary
        ""
        "Commands:"
        commands/usage]
       (string/join \newline)))

(defn get-config []
  (let [homedir (System/getenv "HOME")
        config-path (str homedir "/.fukusu-config.edn")
        config-file (io/file config-path)]
    (if (.exists config-file)
      (clojure.edn/read-string (slurp config-file))
      {:default-regex "."})))

(def cli-options
  [["-a" "--apps REGEX" "Regex to limit action to specific apps."
    :default (re-pattern (:default-regex (get-config)))
    :parse-fn re-pattern]
   ["-h" "--help"]])

(defn -main [& args]
  (let [{:keys [options arguments errors summary]} (parse-opts args cli-options)
        [command-name & arguments] arguments
        command (get commands/all command-name)]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (string/join \newline errors))
      command (command (core/get-app-names (:apps options)) arguments)
      :else (exit 1 (usage summary)))))
