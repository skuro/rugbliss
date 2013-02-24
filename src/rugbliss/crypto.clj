(ns rugbliss.crypto
  (:require [clj-http.client :as client]
            [clojure.string :as string])
  (:import  [javax.xml.bind DatatypeConverter]
            [javax.crypto KeyGenerator
             Cipher]
            [javax.crypto.spec SecretKeySpec
             IvParameterSpec]))

(def api-keys
  {:access-key "m=120688&mk=45d1b598fcc654b317556171f490b989309bae9d"
   :id         "662"
   :key        (.getBytes "cu7ilqF3NvHT7gqz")
   :iv         (.getBytes "gVZOQ1RSlN2w5xpI")})

(def aes-cipher
  (let [aes-keygen (doto
                       (KeyGenerator/getInstance "AES")
                     (.init 128))
        aes-keyspec (SecretKeySpec. (:key api-keys)
                                    "AES")
        aes-ivspec (IvParameterSpec. (:iv api-keys))]
    (doto
        (Cipher/getInstance "AES/CBC/NoPadding")
      (.init Cipher/ENCRYPT_MODE aes-keyspec aes-ivspec))))

(defn base64 [bytes]
  (DatatypeConverter/printBase64Binary bytes))

(defn pad [str]
  (let [bytes (.getBytes str "UTF-8")
        len (alength bytes)
        topad (- 16 (mod len 16))
        buf (byte-array (+ len topad) (byte 0))]
    (System/arraycopy bytes 0 buf 0 len)
    buf))

(defn encrypt-request [req]
  (->> (pad req)
       (.doFinal aes-cipher)
       base64))
