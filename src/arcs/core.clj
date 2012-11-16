(ns arcs.core
  (:use quil.core))

(def tau (* 2 PI))

(def arcs (atom []))

(defn new-arc
  [index]
  {:pos (random tau)
   :width (random (* 0.33 tau) (* 0.8 tau))
   :radius (* 40 (inc index))
   :velocity (random (/ tau 300) (/ tau 120))})

(defn setup []
  (smooth)
  (reset! arcs [])
  (dotimes [i 15]
    (swap! arcs conj (new-arc i))))

(defn tick! []
  (let [arcs' (for [{:keys [pos velocity] :as a} @arcs]
                (assoc a :pos (+ pos velocity)))]
    (reset! arcs arcs')))

(defn draw []
  (tick!)
  (background 0)
  (no-fill)
  (stroke 255)
  (stroke-weight 10)
  (stroke-cap :square)

  (translate (/ (width) 2) (/ (height) 2))
  (doseq [{:keys [pos width radius]} @arcs]
    (arc 0 0 radius radius pos (+ pos width))))

(defsketch arcs-sketch
  :size [1024 768]
  :setup setup
  :draw draw)
