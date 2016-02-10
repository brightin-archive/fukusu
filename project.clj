(defproject fukusu "0.1.0-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.cli "0.3.3"]]

  :plugins [[lein-bin "0.3.5"]]

  :main fukusu.main

  :bin {:name "fukusu"
        :bin-path "bin"
        :bootclasspath true}

  :profiles {:uberjar {:aot :all}})
