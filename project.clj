(defproject fukusu "0.1.0-SNAPSHOT"
  :description "Run commands against multiple Heroku applications"

  :url "https://github.com/brightin/fukusu"

  :license {:name "MIT"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.3"]]

  :plugins [[lein-bin "0.3.5"]]

  :main fukusu.main

  :bin {:name "fukusu"
        :bin-path "bin"
        :bootclasspath true}

  :profiles {:uberjar {:aot :all}})
