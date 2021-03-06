(defproject crimemonitor/crimemonitor "0.1.0-SNAPSHOT"
  :description "A heatmap of crime in South Africa from 2003 - 2014"

  :url "https://github.com/greywolve/crimemonitor"

  :license {:name "MIT License"
            :url "http://www.opensource.org/licenses/mit-license.php"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [org.clojure/tools.logging "0.2.6"]
                 [puppetlabs/trapperkeeper "0.4.2"]
                 [com.datomic/datomic-free "0.9.4815.12"
                  :exclusions [org.clojure/tools.cli
                               org.slf4j/slf4j-nop]]
                 [datomic-schema "1.1.0"]
                 [org.clojure/test.check "0.5.8"]
                 [org.clojure/tools.namespace "0.2.4"]
                 [puppetlabs/trapperkeeper-webserver-jetty9 "0.5.2"]]

  :test-paths ["test" "test-resources"]

  :profiles {:dev {:dependencies [[puppetlabs/trapperkeeper "0.4.2"
                                   :classifier "test"
                                   :scope "test"]
                                  [puppetlabs/kitchensink "0.7.1"
                                   :classifier "test"
                                   :scope "test"]
                                  [clj-http "0.7.9"]
                                  [ring-mock "0.1.5"]]}}

  :repl-options {:init-ns crimemonitor.repl}

  :aliases {"tk" ["trampoline" "run" "--config" "test-resources/config.ini"]}

  :main puppetlabs.trapperkeeper.main)
