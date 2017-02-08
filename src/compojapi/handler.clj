(ns compojapi.handler
  (:require
    [compojapi.db :as db]
    [compojure.api.sweet :as compojure.api]
    [compojure.coercions :as coercions]
    [ring.util.http-response :as http-response]))


(def app
  (compojure.api/api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Offer aggregator"
                    :description ""}
             :tags [{:name "api", :description "offers api"}]}}}

    (compojure.api/context "/api" []
      :tags ["api"]


      (compojure.api/GET "/networks" []
        :summary "networks list"
        (http-response/ok (db/get-networks)))


      (compojure.api/GET "/countries" []
        :query-params [{per-page :- Long 20}
                       {page :- Long 1}]
        :summary "countries list"
        (http-response/ok (db/get-countries page per-page)))


      (compojure.api/GET "/apps" []
        :summary "apps list"
        (db/get-apps))


      (compojure.api/GET "/apps/:id" [id :<< coercions/as-int]
        :summary "app by id"
        (db/get-app-by-id id))


      (compojure.api/GET "/offers" []
        :query-params [{per-page :- Long 20}
                       {page :- Long 1}]
        :summary "offers list"
        (db/get-offers {:page page :per-page per-page})))))
