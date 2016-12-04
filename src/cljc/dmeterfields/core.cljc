(ns dmeterfields.core
  (:require
   [dmeterfields.theme :as theme]
   [om.next :as om :refer [defui]]
   [om.dom :as dom]
   #?@(:cljs [[goog.dom :as gdom]]
       :clj [[garden.color :refer [rgba]]
             [garden.stylesheet :refer [at-media]]
             [garden.units :refer [px percent]]
             [om-style.core :as os]])))

(def app-state (atom {}))

(defui Detail
  #?(:clj static)
  #?(:clj os/Style)
  #?(:clj
     (style [_]
       (list
         (at-media {:min-width (px 768)}
           [:.detail {:flex 1}
            [:&+.detail {:margin-left (px 16)}]])
         (at-media {:max-width (px 767)}
           [:.detail {:display 'flex
                      :flex-direction 'row
                      :align-items 'flex-start
                      :margin-top (px 16)}]
           [:.icon-lrg {:height (px 64)
                        :width (px 64)}]
           [:.icon-stroke {:stroke-width (px 2)}]
           [:.copy {:margin-left (px 16)
                    :flex 1
                    :margin-top 0}]))))
  Object
  (render [this]
    (let [{:keys [title svg-id content]} (om/props this)]
      (dom/li #js {:className "detail"}
        (dom/svg #js {:className "icon-lrg icon-stroke"}
          (dom/create-element "use" #js {:xlinkHref svg-id}))
        (dom/p #js {:className "copy"}
          content)))))

(def detail-view (om/factory Detail
                   {:keyfn :title}))

(defui Details
  #?(:clj static)
  #?(:clj os/Style)
  #?(:clj
     (style [_]
       (list
         (os/get-style Detail)
         (at-media {:min-width (px 768)}
           [:.details {:display 'flex
                       :flex-direction 'row}])
         (at-media {:max-width (px 767)}
           [:.details {:display 'block}]))))
  Object
  (render [this]
    (let [{:keys [details title content]} (om/props this)]
      (dom/section nil
        (dom/div #js {:className "container"}
          (dom/h2 nil title)
          (dom/p nil content)
          (dom/ul #js {:className "details"}
            (mapv detail-view details)))))))

(def details-view (om/factory Details
                    {:keyfn :title}))

(defui Root
  #?(:clj static)
  #?(:clj os/Style)
  #?(:clj
     (style [this]
       (list
         (os/get-style Details)
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
      (details-view {:title "The Farm"
                     :content "Situated in San Simon, Pampanga, the D'Meter Fields Farm is dedicated to the breeding and fattening of cattle within the confines of a clean and bovine-friendly environment."
                     :details [{:title "Australian Cattle"
                                :content "The farm has a total land area of 30 hectares, and is home to 5,800 Brahmans all imported live from Australia."
                                :svg-id "icons.svg#australia"}
                               {:title "Strategic Location"
                                :content "Strategically located near ample and steady sources of forages and concentrate, we're confident we've chosen the right home for our herd. "
                                :svg-id "icons.svg#barn"}
                               {:title "Healthcare"
                                :content "Our in-house veterinarian makes sure every animal on our farm is well taken care of, and as healthy as can be."
                                :svg-id "icons.svg#stethoscope"}]})
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
