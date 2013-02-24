(ns rugbliss.config
  (:require [clojure.java.io :as io]))

; adapted from Leiningen
(defn rugbliss-home
  "Return full path to the user's Rugbliss home directory."
  []
  (let [rb-home (System/getenv "RUGBLISS_HOME")
        rb-home (or (and rb-home (io/file rb-home))
                      (io/file (System/getProperty "user.home") ".rugbliss"))]
    (.getAbsolutePath (doto rb-home .mkdirs))))

(defn read-file
  "Read the contents of file if it exists."
  [file]
  (if (.exists file)
    (read-string (slurp file))))

(def config (read-file (io/file (rugbliss-home) "config.clj")))
