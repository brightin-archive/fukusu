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

(def command-usage
  (->>
   (for [[name fn] output/commands]
     (format "  %-15s # %s" name (:doc (meta fn))))
   (string/join \newline)))

(defn usage [options-summary]
  (->> ["Fukusu: run Heroku commands against multiple apps"
        ""
        "Usage: fukusu action [options]"
        ""
        "Options:"
        options-summary
        ""
        "Actions:"
        command-usage]
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
        [command-name & arguments] arguments
        app-regex (:apps options)]
    (cond
      (:help options) (exit 0 (usage summary))
      errors (exit 1 (error-msg errors)))
    (if-let [command (get output/commands command-name)]
      (command app-regex arguments)
      (exit 1 (usage summary)))))
