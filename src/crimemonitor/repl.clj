(ns crimemonitor.repl
  (:require [puppetlabs.trapperkeeper.services.webserver.jetty9-service
              :refer [jetty9-service]]
            [crimemonitor.services.core :refer [simple-service datomic-service hello-web-service]]
            [puppetlabs.trapperkeeper.core :as tk]
            [puppetlabs.trapperkeeper.app :as tka]
            [clojure.tools.namespace.repl :refer (refresh)]))

(def system nil)

(defn init []
  (alter-var-root #'system
                  (constantly (tk/build-app
                               [jetty9-service
                                simple-service
                                hello-web-service
                                datomic-service]
                               {:global {:logging-config "test-resources/logback-dev.xml"}
                                :webserver {:host "localhost"
                                            :port 8080 }
                                :datomic   {:uri
                                            "datomic:free://localhost:4334/crimemonitor"}
                                :crimemonitor {:url-prefix "/crimemonitor"}})))
  (alter-var-root #'system tka/init)
  #_(tka/check-for-errors! system))

(defn start []
  (alter-var-root #'system
                  (fn [s] (if s (tka/start s))))
  #_(tka/check-for-errors! system))

(defn stop []
  (alter-var-root #'system
                  (fn [s] (if s (tka/stop s)))))

(defn go []
  (init)
  (start))

(defn context []
  @(tka/app-context system))

(defn print-context []
  (clojure.pprint/pprint (context)))

(defn reset []
  (stop)
  (refresh :after 'examples.ring-app.repl/go))
