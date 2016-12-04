(ns dmeterfields.core
  (:require
   [om.next :as om :refer [defui]]
   [om.dom :as dom]
   #?@(:cljs [[goog.dom :as gdom]]
       :clj [[garden.units :refer [px percent]]
             [garden.color :refer [rgba]]
             [om-style.core :as os]])))

(def app-state (atom {}))

(defui Root
  #?(:clj static)
  #?(:clj os/Style)
  #?(:clj
     (style [this]
       (list
         [:body {:font-family "Roboto"}]
         [:.toolbar {:text-align 'left
                     :padding-left (px 16)
                     :padding-right (px 16)
                     :height (px 64)
                     :background-color (rgba 0 0 0 0.4)}]
         [:h1#brand {:font-size (px 18)
                     :line-height (px 64)
                     :color 'white
                     :margin 0}]
         [:#hero {:background-image "url(../steak.jpg)"
                  :height (px 640)
                  :background-size 'cover
                  :background-position [[(percent 50) 0]]
                  :background-repeat 'no-repeat
                  :overflow 'hidden
                  :color 'white
                  :text-align 'center
                  :position 'relative}
          [:h2 {:top (percent 50)
                :transform "translateY(-50%)"
                :margin-top 0
                :margin-bottom 0
                :position 'relative
                :font-size (px 48)}]])))
  Object
  (render [this]
    (dom/div nil
      (dom/section #js {:id "hero"}
        (dom/div #js {:className "toolbar"}
          (dom/h1 #js {:id "brand"}
            "D'meter Fields"))
        (dom/h2 #js {:className "container"}
          "High quality meat from the farm all the way down to your business."))
      (dom/div nil "testing"))))

(def reconciler
  (om/reconciler {:state app-state}))

#?(:cljs
   (om/add-root! reconciler
     Root
     (gdom/getElement "app")))
