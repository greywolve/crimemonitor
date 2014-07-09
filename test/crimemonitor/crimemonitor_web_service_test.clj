(ns crimemonitor.crimemonitor-web-service-test
  (:require [clojure.test :refer :all]
            [puppetlabs.trapperkeeper.app :as app]
            [puppetlabs.trapperkeeper.testutils.bootstrap :refer [with-app-with-config]]
            [puppetlabs.trapperkeeper.services.webserver.jetty9-service :refer [jetty9-service]]
            [clj-http.client :as client]
            [crimemonitor.services.core :as svc]))

(deftest hello-web-service-test
  (testing "says hello to caller"
    (with-app-with-config app
      [svc/simple-service
       svc/hello-web-service
       svc/datomic-service
       jetty9-service]
      {:global {:logging-config "test-resources/logback-dev.xml"}
       :webserver {:host "localhost"
                   :port 8080 }
       :datomic   {:uri
                   "datomic:free://localhost:4334/crimemonitor"}
       :crimemonitor {:url-prefix "/crimemonitor"}}
      (let [resp (client/get "http://localhost:8080/hello/foo")]
        (is (= "Hello, foo!" (:body resp)))))))
