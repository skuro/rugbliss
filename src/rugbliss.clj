(ns rugbliss
  (:gen-class)
  (:use rugbliss.commands)
  (:require rugbliss.api))

(defn interactive []
  (while true
    (print "> ")
    (flush)
    (let [command (read-line)]
      (execute command))))

(defn -main [& args]
  (println "Welcome to Rugbliss v0.1 -- © Carlo Sciolla")
  (println "Type 'help' for the list of available commands")
  (interactive))
