(ns rugbliss.http
  (:require [clj-http.client :as client])
  (:use rugbliss.crypto))

(defn choose-protocol [params]
  (if (some #{:ssl} params)
    "https://"
    "http://"))

(defn encode [params]
  (client/generate-query-string params))

(defn get-url [req & params]
  (let [url-req (encode req)
        proto (choose-protocol params)
        enc (encrypt-request url-req)
        std-params {:d (:id api-keys)
                    :er enc}]
    (str proto "api.blackoutrugby.com/?" (encode std-params) "&" url-req)))

(defn post-url [req & params]
  (let [url-req (encode req)
        proto (choose-protocol params)
        std-params {:d (:id api-keys)}]
    (str proto "api.blackoutrugby.com/?" (encode std-params))))
