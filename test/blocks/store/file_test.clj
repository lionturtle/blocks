(ns blocks.store.file-test
  (:require
    (blocks.store
      [file :as file :refer [file-store]]
      [tests :refer [test-block-store]])
    [clojure.java.io :as io]
    [clojure.test :refer :all]))


(deftest conversion-tests
  (testing "file->id"
    (let [file->id @#'file/file->id]
      (is (nil? (file->id nil nil))
          "should return nil for nil files")
      (is (nil? (file->id "foo/bar" (io/file "foo" "baz" "abc")))
          "should return nil for non-child files")
      (is (nil? (file->id "foo/bar" (io/file "foo" "bar" "1220" "ab" "0x" "123abc")))
          "should return nil for non-hex files"))))


(deftest test-file-store
  (let [tmpdir (io/file "target" "test" "tmp"
                        (str "file-block-store."
                          (System/currentTimeMillis)))
        store (file-store tmpdir)]
    (test-block-store
      "file-store" store
      :eraser file/erase!
      :max-size 1024
      :blocks 10)))