(ns wareblog.core-test
  (require [clojure.edn :as edn :refer [read-string]]
           [clojure.test :refer [deftest is testing use-fixtures]]
           [wareblog.core :refer :all]
           [org.httpkit.client :as http-client :refer [get]]))

(deftest a-test
  (testing "FIXME, I fail."
    (is (= 1 1))
    (is (> 3 2))))

(defn server-setup-and-teardown [f]
  (start-server)
  (f)
  (stop-server))

(use-fixtures :once server-setup-and-teardown)

(deftest handle-a-request
    (testing "We will do some REST API testing here"
      (is (= 1 1))
      (let [{:keys [status body headers] :as resp} @(http-client/get "http://localhost:3000/index.html")]
                 (is (= (str status) "200"))
                 (is (= (:server headers) "http-kit")))
      ))

;(edn/read-string {:readers edn-readers} (slurp (:body @(http-client/get "http://localhost:3000/articles/abc"))))
