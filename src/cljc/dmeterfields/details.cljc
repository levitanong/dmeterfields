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
         #_[:.details {:padding-bottom (px 16)
                     :padding-top (px 16)}]
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
