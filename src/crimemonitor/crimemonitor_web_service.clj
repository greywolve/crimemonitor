(ns crimemonitor.crimemonitor-web-service
  (:require [clojure.tools.logging :as log]
            [compojure.core :as compojure]
            [crimemonitor.crimemonitor-web-core :as core]
            [puppetlabs.trapperkeeper.core :as trapperkeeper]))

(trapperkeeper/defservice hello-web-service
  [[:ConfigService get-in-config]
   [:WebserverService add-ring-handler]
   HelloService]
  (init [this context]
    (log/info "Initializing hello webservice")
    (let [url-prefix (get-in-config [:hello-web :url-prefix])]
      (add-ring-handler
        (compojure/context url-prefix []
          (core/app (get-service :HelloService)))
        url-prefix)
      (assoc context :url-prefix url-prefix))))
