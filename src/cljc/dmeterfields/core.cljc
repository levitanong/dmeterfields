(ns dmeterfields.core
  (:require
   [om.next :as om :refer [defui]]
   [om.dom :as dom]
   #?(:cljs [goog.dom :as gdom])))

(def app-state (atom {}))

(defui Root
  Object
  (render [this]
    (dom/div nil "om loaded testing TESTING")))

(def reconciler
  (om/reconciler {:state app-state}))

#?(:cljs
   (om/add-root! reconciler
     Root
     (gdom/getElement "app")))