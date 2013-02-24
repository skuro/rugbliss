(ns rugbliss.crypto
  (:require [clj-http.client :as client]
            [clojure.string :as string])
  (:import  [javax.xml.bind DatatypeConverter]
            [javax.crypto KeyGenerator
             Cipher]
            [javax.crypto.spec SecretKeySpec
             IvParameterSpec])
  (:use rugbliss.config))

(def api-keys (:api-keys config))

(def aes-cipher
  (let [aes-keygen (doto
                       (KeyGenerator/getInstance "AES")
                     (.init 128))
        aes-keyspec (SecretKeySpec. (.getBytes (:key api-keys))
                                    "AES")
        aes-ivspec (IvParameterSpec. (.getBytes (:iv api-keys)))]
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
