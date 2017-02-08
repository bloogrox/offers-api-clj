(ns compojapi.config)


(def database-url
  (if-let [database-url (System/getenv "DATABASE_URL")]
    database-url
    "postgres://user:1a2s3d4f@0.0.0.0:5432/cpa"))
