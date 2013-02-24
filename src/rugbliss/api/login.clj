(ns rugbliss.api.login
  (:require [clj-http.client :as client]
            [clojure.xml :as xml])
  (:use rugbliss.crypto
        rugbliss.http
        rugbliss.commands
        rugbliss.session)
  (:import java.io.StringReader
           org.xml.sax.InputSource))

(defn login-request [user pass]
  (let [url (post-url {:r "lgn"} :ssl)
        params {:username user
                :password pass
                :r "lgn"}
        crypt (encrypt-request (encode params))]
    (client/post url
                 {:form-params (merge params {:er crypt})})))

(defn parse-response [body]
  (-> body
      (StringReader.)
      (InputSource.)
      xml/parse))

(defn extract-value [info key]
  (->> info
       :content
       (filter #(= key (:tag %)))
       first
       :content
       first))

(defn update-session [info user]
  (let [league (select-keys (:attrs info) [:season :round :day])
        memberid (extract-value info :memberid)
        teamid (extract-value info :teamid)]
    (add (merge league {:memberid memberid
                        :teamid teamid
                        :user user}))))

(defcommand login
  "Logs the user into the application"
  []
  (let [username (input-param "username")
        password (input-password "password")
        response (login-request username password)]
    (update-session (parse-response (:body response)) username)
    (println "Login ok.")))
