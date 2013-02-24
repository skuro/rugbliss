(ns rugbliss.commands)

(defonce commands (atom {}))

(defmacro defcommand [name desc [& params] & forms]
  `(do (defn ~name [~@(map symbol params)] ~@forms)
       (swap! commands assoc ~(keyword name) (with-meta ~name {:doc ~desc}))))

(defcommand unknown
  "Command not found handler"
  [& _]
  (println "Invalid command"))

(defcommand quit
  "Exits Rugbliss"
  []
  (println "Bye.")
  (System/exit 0))

(defcommand help
  "Prints this help screen"
  []
  (println)
  (letfn [(help-text [[key val]]
            (let [command (symbol (name key))
                  doc (:doc (meta val))]
              (printf "\t%s\t\t%s\n" command doc)
              (println)))]
    (println "Available commands:")
    (println)
    (dorun (map help-text @commands))))

(defn get-command [command]
  (let [c (keyword command)]
    (if-let [command-fn (get @commands c)]
      command-fn
      (get @commands :unkwnown))))

(defn execute [command & args]
  (if-let [command-fn (get-command command)]
    (apply command-fn args)
    (apply unknown args)))

(defn- format-out [text]
  (print (str "\t" text ": "))
  (flush))

(defn input-param [text]
  (format-out text)
  (read-line))

(defn input-password [text]
  (let [console (System/console)]
    (format-out text)
    (String. (.readPassword console))))
