(ns compojapi.api-test
  (:require
    [cheshire.core :as cheshire]
    [clojure.test :refer :all]
    [compojapi.handler :as handler]
    [ring.mock.request :as mock]))


(defn parse-body [body]
  (cheshire/parse-string (slurp body) true))

(deftest api-tests

  (testing "Test GET /api/networks"
    (let [response (handler/app (-> (mock/request :get  "/api/networks")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (seq? body))))

  (testing "Test GET /api/countries"
    (let [response (handler/app (-> (mock/request :get  "/api/countries?per-page=1")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (seq? body))
      (is (= (count body) 1))))

  (testing "Test GET /api/apps"
    (let [response (handler/app (-> (mock/request :get  "/api/apps")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (seq? body))))

  (testing "Test GET /api/offers"
    (let [response (handler/app (-> (mock/request :get  "/api/offers")))
          body     (parse-body (:body response))]
      (is (= (:status response) 200))
      (is (seq? body)))))
