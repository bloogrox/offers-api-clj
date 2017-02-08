(ns compojapi.db
  (:require
    [compojapi.config :as config]
    [heroku-database-url-to-jdbc.core :as heroku-database-url-to-jdbc]
    [korma.core :as korma]
    [korma.db]))


(korma.db/defdb prod
  (korma.db/postgres
    (heroku-database-url-to-jdbc/korma-connection-map config/database-url)))


(declare
  app
  country
  network
  offer
  offer_countries
  platform)


(korma/defentity network
  (korma/database prod)
  (korma/entity-fields :id :name))


(korma/defentity country
  (korma/pk :code)
  (korma/entity-fields :code)
  (korma/has-many offer_countries))


(korma/defentity platform
  (korma/entity-fields :id :name)
  (korma/has-many app))


(korma/defentity app
  ; (pk :id)
  ; (database prod)
  (korma/entity-fields :id :app_id :icon_url :platform_id)
  (korma/belongs-to platform))

(korma/defentity offer
  ; (pk :id)
  ; (database prod)
  (korma/entity-fields :id :remote_id :name :payout :created :status :preview_url
                 :app_id :network_id)
  (korma/belongs-to network)
  (korma/belongs-to app)
  (korma/has-many offer_countries))


(korma/defentity offer_countries
  (korma/table :offer_countries)
  (korma/entity-fields :offer_id :country_code)
  (korma/belongs-to offer)
  (korma/belongs-to country {:fk :country_code}))


(defn get-networks []
  (korma/select network
    (korma/order :name :ASC)))


(defn get-countries [page per-page]
  (let [offset_ (* (dec page) per-page)]
    (korma/select country
      (korma/limit per-page)
      (korma/offset offset_)
      (korma/order :code :ASC))))


(defn get-apps []
  (korma/select app
    (korma/limit 10)))

(defn get-app-by-id [id]
  (korma/select app
    (korma/where {:id id})))

(defn get-offers [{page :page per-page :per-page}]
  (let [offset_ (* (dec page) per-page)]
    (korma/select offer
      (korma/limit per-page)
      (korma/offset offset_))))
