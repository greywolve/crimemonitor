(defproject crimemonitor/crimemonitor "0.1.0-SNAPSHOT"
  :description "A heatmap of crime in South Africa from 2003 - 2014"

  :url "https://github.com/greywolve/crimemonitor"

  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [compojure "1.1.6"]
                 [org.clojure/tools.logging "0.2.6"]
                 [puppetlabs/trapperkeeper "0.3.4"]
                 [com.datomic/datomic-free "0.9.4815.12"
                  :exclusions [org.clojure/tools.cli
                               org.slf4j/slf4j-nop]]]

  :test-paths ["test" "test-resources"]

  :profiles {:dev {:dependencies [[puppetlabs/trapperkeeper-webserver-jetty9 "0.3.3"]
                                  [puppetlabs/trapperkeeper "0.3.4"
                                   :classifier "test"
                                   :scope "test"]
                                  [puppetlabs/kitchensink "0.5.3"
                                   :classifier "test"
                                   :scope "test"]
                                  [clj-http "0.7.9"]
                                  [ring-mock "0.1.5"]]}}

  :aliases {"tk" ["trampoline" "run" "--config" "test-resources/config.ini"]}

  :main puppetlabs.trapperkeeper.main)
