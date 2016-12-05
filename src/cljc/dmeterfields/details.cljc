(ns dmeterfields.details
  (:require
   [dmeterfields.theme :as theme]
   [om.next :as om :refer [defui]]
   [om.dom :as dom]
   #?@(:cljs [[goog.dom :as gdom]]
       :clj [[garden.color :refer [rgba]]
             [garden.stylesheet :refer [at-media]]
             [garden.units :refer [px percent]]
             [om-style.core :as os]])))

(defui Detail
  #?(:clj static)
  #?(:clj os/Style)
  #?(:clj
     (style [_]
       (list
         [:.pull-text {:height (px (- 128 8))
                       :width (px (- 128 8))
                       :margin 0
                       :font-weight 300
                       :line-height (px (- 128 8))
                       :font-size (px 72)
                       :font-family "\"Abel\", \"Arial\""
                       :text-align 'center
                       :border [[(px 4) 'solid (:dark theme/color)]]}]
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
           [:.pull-text {:height (px (- 64 4))
                         :width (px (- 64 4))
                         :font-size (px 32)
                         :line-height (px (- 64 4))
                         :border-width (px 2)}]
           [:.icon-stroke {:stroke-width (px 2)}]
           [:.copy {:margin-left (px 16)
                    :flex 1
                    :margin-top 0}]))))
  Object
  (render [this]
    (let [{:keys [title svg-id content pull-text color]} (om/props this)]
      (dom/li #js {:className "detail"}
        (when pull-text
          (dom/h1 #js {:className "pull-text"
                       :style #js {:borderColor (or color nil)}}
            pull-text))
        (when svg-id
          (dom/svg #js {:className "icon-lrg icon-stroke"
                        :style #js {:stroke (or color nil)}}
            (dom/create-element "use" #js {:xlinkHref svg-id})))
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
         #_[:.details {:padding-bottom (px 16)
                     :padding-top (px 16)}]
         (at-media {:min-width (px 768)}
           [:.details {:display 'flex
                       :flex-direction 'row}])
         (at-media {:max-width (px 767)}
           [:.details {:display 'block}]))))
  Object
  (render [this]
    (let [{:keys [details title content
                  bg-color color]} (om/props this)]
      (dom/section #js {:className "section"
                        :style #js {:backgroundColor
                                    (or bg-color "transparent")
                                    :color
                                    (or color "inherit")}}
        (dom/div #js {:className "container"}
          (dom/h2 nil title)
          (dom/p nil content)
          (dom/ul #js {:className "details"}
            (mapv (fn [detail]
                    (detail-view (assoc detail :color color)))
              details)))))))

(def details-view (om/factory Details
                    {:keyfn :title}))
