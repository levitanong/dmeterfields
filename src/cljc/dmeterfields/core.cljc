(ns dmeterfields.core
  (:require
   [dmeterfields.data :as data]
   [dmeterfields.details :as details]
   [dmeterfields.theme :as theme]
   [om.next :as om :refer [defui]]
   [om.dom :as dom]
   #?@(:cljs [[goog.dom :as gdom]]
       :clj [[garden.color :refer [rgba]]
             [garden.stylesheet :refer [at-media]]
             [garden.units :refer [px percent]]
             [om-style.core :as os]])))

(def app-state (atom {}))

(defui Root
  #?(:clj static)
  #?(:clj os/Style)
  #?(:clj
     (style [this]
       (list
         (os/get-style details/Details)
         [:body {:font-family "Roboto"}]
         [:.toolbar {:text-align 'left
                     :padding-left (px 16)
                     :padding-right (px 16)
                     :height (px 64)
                     :background-color (rgba 0 0 0 0.4)
                     :position 'absolute
                     :z-index 2
                     :top 0 :left 0 :right 0}]
         [:h1#brand {:font-size (px 18)
                     :line-height (px 64)
                     :color 'white
                     :margin 0}]
         [:#hero {:height (px 640)
                  :background-color 'black
                  :color (rgba 255 255 255 0.8)
                  :position 'relative
                  :boz-sizing 'border-box
                  :padding (px 24)}
          [:&:after {:content "\"\""
                     :background-image "url(../steak.jpg)"
                     :background-size 'cover
                     :background-position [[(percent 50) 0]]
                     :background-repeat 'no-repeat
                     :position 'absolute
                     :z-index 1
                     :opacity 0.6
                     :top 0 :left 0 :right 0 :bottom 0}]
          [:.hero-text {:top (percent 50)
                        :transform "translateY(-50%)"
                        :margin-top 0
                        :margin-bottom 0
                        :max-width (px 720)
                        :text-align 'center
                        :position 'relative
                        :z-index 2}
           [:h2 {:font-size (px 48)
                 :margin-top 0}]
           [:p {:margin-bottom 0
                :line-height 1.5}]]]
         [:#integration {:background-color "#92b7b3"}
          [:#integration-blurb {:font-style "italic"}]]
         (at-media {:max-width (px 767)}
           [:#hero {:height (px 520)}
            [:.hero-text
             [:h2 {:font-size (px 36)}]]]))))
  Object
  (render [this]
    (dom/div nil
      (dom/section #js {:id "hero"}
        (dom/div #js {:className "toolbar"}
          (dom/h1 #js {:id "brand"}
            "D'meter Fields"))
        (dom/div #js {:className "container hero-text"}
          (dom/h2 nil
            "High quality meat from the farm all the way down to your business.")
          (dom/p nil
            "D'Meter Fields is dedicated to bringing your business the highest quality meat by tightly integrating advanced farm and feed techniques with the processing, storage, and delivery facilities of its sister company, SSMPC.")))
      (details/details-view data/farm)
      (dom/div #js {:id "integration"}
        (dom/section #js {:id "integration-blurb"}
          (dom/div #js {:className "container"}
            (dom/h2 nil
              "Unparalleled integration")
            (dom/p nil
              "Together with our sister company SN Smn Meat Products Corporation (SSMPC), we make sure our high quality meat from the farm stays high quality all the way to your business. Here's how we do it.")))
        (mapv details/details-view
          data/integration))
      (dom/footer nil
        (dom/div #js {:className "container"}
          (dom/p nil
            "Australia by Hea Poh Lin from the Noun Project"))))))

(def reconciler
  (om/reconciler {:state app-state}))

#?(:cljs
   (om/add-root! reconciler
     Root
     (gdom/getElement "app")))
