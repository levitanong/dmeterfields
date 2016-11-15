(ns dmeterfields.core
  (:require
   [om.next :as om :refer-macros [defui]]
   [om.dom :as dom]
   [goog.dom :as gdom]))

(def app-state (atom {}))

(defui Root
  Object
  (render [this]
    (dom/div nil "om loaded testing TESTING")))

(def reconciler
  (om/reconciler {:state app-state}))

(om/add-root! reconciler
  Root
  (gdom/getElement "app"))
