(ns crimemonitor.handler
  (:require [clojure.tools.logging :as log]
            [compojure.core :refer :all]
            [compojure.handler :as handler]
            [compojure.route :as route]))

(defn root-view [caller]
  (str "Sup, " caller))

(defroutes app-routes
  (GET "/:caller" [caller] (root-view caller))
  (route/not-found "Not Found"))

(defn app [url-prefix] (context url-prefix [] (handler/site app-routes)))
