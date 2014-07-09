(ns crimemonitor.services.core
  "Contains the core services for CrimeMonitor"
  (:require [clojure.tools.logging :as log]
            [puppetlabs.trapperkeeper.core :as tk]
            [crimemonitor.handler :as handler]
            [datomic.api :as d]))

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
    (let [path (get-in-config [:crimemonitor :url-prefix])]
      (add-ring-handler (handler/app path) path)
      (assoc context :url-prefix path))))

(defprotocol DatomicService
  (db [this]))

(tk/defservice datomic-service
  DatomicService
  [[:ConfigService get-in-config]]
  (init [this context]
        (log/info "Initializing datomic service")
    (let [uri (get-in-config [:datomic :uri])]
      (d/create-database uri)
      (assoc context :conn (d/connect uri))))
  (start [this context]
         (log/info "Starting datomic service")
         context)
  (stop [this context]
        (log/info "Stopping datomic service")
        (d/release (:conn context))
        context)
  (db [this]
      (let [conn (:conn (service-context this))]
        (d/db conn))))
