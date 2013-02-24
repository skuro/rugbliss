(ns rugbliss.session)

(defonce session (atom {}))

(defn add
  ([m]
     (swap! session merge m))
  ([k v]
     (swap! session assoc k v))
  ([k v & others]
     (add
      (reduce
       (fn [acc [a b]]
         (assoc acc a b)) {k v} others))))

(defn delete [k]
  (swap! session dissoc k))

(defn values
  ([k]
     (if (vector? k)
       (apply values k)))
  ([h & t]
     (mapv #(get @session %) (cons h t))))
