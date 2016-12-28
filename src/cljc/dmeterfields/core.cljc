(ns dmeterfields.core
  (:refer-clojure :exclude [read])
  (:require
   [dmeterfields.data :as data]
   [dmeterfields.details :as details]
   [dmeterfields.theme :as theme]
   [om.next :as om :refer [defui]]
   [om.dom :as dom]
   #?@(:cljs [[goog.dom :as gdom]
              [goog.string :as gstring]]
       :clj [[garden.color :as color :refer [rgba]]
             [garden.stylesheet :refer [at-media]]
             [garden.units :refer [px percent]]
             [om-style.core :as os]])))

(def app-state (atom {:app/current-tab :abattoir}))

(def obfuscated-email
  (let [o "&#099;&#111;&#110;&#116;&#097;&#099;&#116;&#064;&#100;&#109;&#101;&#116;&#101;&#114;&#102;&#105;&#101;&#108;&#100;&#115;&#046;&#099;&#111;&#109;"]
    #?(:cljs (gstring/unescapeEntities o)
       :clj o)))

(defui CallToAction
  #?(:clj static)
  #?(:clj os/Style)
  #?(:clj
     (style [this]
       (list
         [:.call-to-action
          {:color (:light theme/color)
           :cursor 'pointer
           :border-radius (px 3)
           :text-decoration 'none
           :display 'inline-block
           :padding [[(px 16) (px 24)]]
           :font-size (px 24)
           :background-color 'transparent
           :border [[(px 1) 'solid (:light theme/color)]]}])))
  Object
  (render [this]
    (dom/a #js {:className "call-to-action"
                :style #js {:marginTop "32px"}
                :href (str "mailto:" obfuscated-email)}
      "Contact Us!")))

(def call-to-action (om/factory CallToAction))

(defui Root
  #?(:clj static)
  #?(:clj os/Style)
  #?(:clj
     (style [this]
       (list
         (os/get-style details/Details)
         (os/get-style CallToAction)
         [:.toolbar {:text-align 'left
                     :padding-left (px 16)
                     :padding-right (px 16)
                     :height (px 64)
                     :position 'relative
                     :border-bottom [[(px 1) 'dashed (:light theme/color)]]
                     ;; :background-color (rgba 0 0 0 0.4)
                     :z-index 2
                     ;; :top 0 :left 0 :right 0
                     }]
         [:h1#brand {:font-size (px 21)
                     :font-family "\"Pacifico\", \"Georgia\", \"Arial\""
                     :font-weight 'normal
                     :line-height (px 64)
                     :color (:light theme/color)
                     :margin 0}]
         [:#hero {:height (px 640)
                  :background-color (:dark theme/color)
                  :color (:light theme/color)
                  :position 'relative
                  :boz-sizing 'border-box
                  :padding (px 0)}
          [:&:before {:content "\"\""
                     :background-image "url(http://dmeterfields.com/steak.jpg)"
                     :background-size 'cover
                     :background-position [[(percent 50) 0]]
                     :background-repeat 'no-repeat
                     :position 'absolute
                     :z-index 1
                     :opacity 0.6
                     :top 0 :left 0 :right 0 :bottom 0}]
          [:.hero-text {:padding (px 24)
                        :margin-top 0
                        :margin-bottom 0
                        :max-width (px 720)
                        :position 'relative
                        :z-index 2
                        :text-align 'center}
           [:h2 {:font-size (px 36)
                 :margin-top 0}]
           [:p {:margin-bottom 0
                :line-height 1.5}]]]
         [:#integration {:position 'relative}
          [:.separator {:position 'absolute
                        :top (percent 50)
                        :left 0
                        :right 0
                        :z-index 0}]
          [:#integration-blurb {:border [[(px 1) 'solid (:dark theme/color)]]
                                :position 'relative
                                :z-index 1
                                :background-color 'white
                                :padding [[(px 16) (px 32)]]
                                :max-width (px 640)
                                :margin [[0 'auto]]
                                :font-style 'italic
                                :text-align 'center}]]
         [:hr {:border-top 'none}
          [:&.dark {:border-bottom [[(px 1) 'dashed (:dark theme/color)]]}]
          [:&.light {:border-bottom [[(px 1) 'dashed (:light theme/color)]]}]]
         (at-media {:max-width (px 767)}
           [:#hero {:height (px 640)}
            [:.hero-text
             [:h2 {:font-size (px 36)}]]])
         [:footer {:background-color (:dark theme/color)
                   :padding [[(px 16) 0]]
                   :color (:light theme/color)}])))
  static om/IQuery
  (query [this]
    [:app/current-tab])
  Object
  (render [this]
    (let [{:keys [app/current-tab]} (om/props this)]
      (dom/div nil
        (dom/section #js {:id "hero"
                          :className "v stack"}
          (dom/div #js {:className "toolbar"}
            (dom/h1 #js {:id "brand"}
              "D‘Meter Fields"))
          (dom/div #js {:className "v stack grow container hero-text center center-justify"}
            (dom/h2 nil
              "High quality meat from the farm all the way down to your business.")
            (dom/p nil
              "D'Meter Fields is dedicated to bringing your business the highest quality meat by tightly integrating advanced farm and feed techniques with the processing, storage, and delivery facilities of its sister company, SSMPC.")
            (call-to-action)))
        (dom/section nil
          (details/details-view data/farm))
        (dom/section #js {:id "integration"}
          (dom/hr #js {:className "separator dark"})
          (dom/div #js {:className "container"}
            (dom/div #js {:id "integration-blurb"}
              (dom/h2 nil
                "Vertical Integration")
              (dom/p nil
                "Together with our sister company SN Smn Meat Products Corporation (SSMPC), we make sure our high quality meat from the farm stays high quality all the way to your business. Here's how we do it."))))
        (dom/section nil
          (dom/div #js {:style #js {:textAlign "center"}}
            (dom/div #js {:className "tight button-group"
                          :style #js {:display "inline-block"}}
              (->> [{:key :abattoir
                     :label "Abattoir"}
                    {:key :cold
                     :label "Cold Facilities"}]
                (mapv
                  (fn [{:keys [key label]}]
                    (dom/button #js {:key key
                                     :className (str "button"
                                                  (when (= current-tab key)
                                                    " active"))
                                     :onClick #(om/transact! this
                                                 `[(tab/select ~{:key key})])}
                      label))))))
          (case current-tab
            :abattoir (details/details-view data/abattoir)
            :cold (details/details-view data/cold)))
        (dom/footer nil
          (dom/div #js {:className "container"}
            (dom/div #js {:style #js {:textAlign "center"
                                      :marginBottom "64px"}}
              (call-to-action)
              (dom/h2 nil "We'd love to hear from you!")))
          (dom/hr #js {:className "light"})
          (dom/div #js {:className "container h stack"
                        :style #js {:justifyContent "space-between"
                                    :alignItems "center"}}
            (dom/p nil
              "Copyright D'Meter Fields Corporation 2016")
            (dom/small #js {:className "faint"}
              "Australia Illustration by Hea Poh Lin from the Noun Project")))))))

(defmulti read om/dispatch)
(defmethod read :default
  [{:keys [state]} k _]
  (let [st @state]
    {:value (get st k)}))

(defmulti mutate om/dispatch)
(defmethod mutate 'tab/select
  [{:keys [state]} k {:keys [key]}]
  {:action #(swap! state assoc :app/current-tab key)})

(def reconciler
  (om/reconciler {:state app-state
                  :parser (om/parser {:read read
                                      :mutate mutate})}))

#?(:cljs
   (om/add-root! reconciler
     Root
     (gdom/getElement "app")))
