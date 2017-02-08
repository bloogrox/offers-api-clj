(defproject compojapi "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [metosin/compojure-api "1.1.10"]
                 [korma "0.4.0"]
                 [org.postgresql/postgresql "9.2-1002-jdbc4"]
                 [heroku-database-url-to-jdbc "0.2.2"]
                 [proto-repl "0.3.1"]]
  :min-lein-version "2.0.0"
  :ring {:handler compojapi.handler/app}
  :uberjar-name "server.jar"
  :profiles {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                                  [cheshire "5.5.0"]
                                  [ring/ring-mock "0.3.0"]]
                   :plugins [[lein-ring "0.10.0"]
                             [com.jakemccrary/lein-test-refresh "0.18.1"]]}})
             
