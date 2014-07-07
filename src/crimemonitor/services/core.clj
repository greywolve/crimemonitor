(ns crimemonitor.services.core
  "Contains the core services for CrimeMonitor"
  (:require [clojure.tools.logging :as log]
            [puppetlabs.trapperkeeper.core :as tk]
            [crimemonitor.handler :as handler]))

(defprotocol SimpleService
  (greet [this caller]))

(tk/defservice simple-service
  SimpleService
  []
  (init [this context]
        (log/info "Initializing simple service")
        (log/info (str "The context is " context))
        context)
  (start [this context]
         (log/info "Starting simple service")
         context)
  (stop [this context]
        (log/info "Stopping simple service")
        context)
  (greet [this caller]
         (format "Greetings, %s!" caller)))

(tk/defservice hello-web-service
  [[:ConfigService get-in-config]
   [:WebserverService add-ring-handler]
   SimpleService]
  (init [this context]
    (log/info "Initializing hello webservice")
    (log/info ((:greet SimpleService) "Olly"))
    (let [path (get-in-config [:hello-web :url-prefix])]
      (add-ring-handler (handler/app path) path)
      (assoc context :url-prefix path))))
