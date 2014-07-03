(ns crimemonitor.crimemonitor-core-test
  (:require [clojure.test :refer :all]
            [crimemonitor.crimemonitor-core :refer :all]))

(deftest hello-test
  (testing "says hello to caller"
    (is (= "Hello, foo!" (hello "foo")))))
