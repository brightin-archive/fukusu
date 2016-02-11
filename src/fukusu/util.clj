(ns fukusu.util
  (:require [clojure.string :as string]))

(defn long-str [& strings]
  (string/join \newline strings))

(defn split-by-space [str]
  (string/split str #"\s"))

(defn split-by-dash [str]
  (string/split str #"-"))
