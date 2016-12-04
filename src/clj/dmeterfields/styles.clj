(ns dmeterfields.styles
  (:require
   [dmeterfields.theme :as theme]
   [garden.color :refer [rgba]]
   [garden.stylesheet :refer [at-media]]
   [garden.units :refer [px s percent]]))

(def layout
  (list
    [:.stack {:display 'flex}
     [:&.v {:flex-direction 'column}]
     [:&.h {:flex-direction 'row}]
     [:&.stretch {:align-items 'stretch}]
     [:&.center {:align-items 'center}]
     [:&.center-justify {:justify-content 'center}]
     [:&.gutters
      [:&.v>*+* {:margin-top (px 16)}]
      [:&.h>*+* {:margin-left (px 16)}]]
     [:&.gutters-lrg
      [:&.v>*+* {:margin-top (px 24)}]
      [:&.h>*+* {:margin-left (px 24)}]]]
    [:.grow {:flex 1}]
    [:.pad {:padding [[(px 12) (px 16)]]
            :box-sizing 'border-box}]
    [:.toolbar.pad :.toolbar>.pad
     {:padding [[0 (px 12)]]}]
    [:.relative {:position 'relative}]
    [:section {:padding-top (px 24)
               :padding-bottom (px 24)}]
    [:.container {:max-width (px 940)
                  :width (percent 100)
                  :margin-left 'auto
                  :margin-right 'auto
                  :box-sizing 'border-box}]
    (at-media {:max-width (px 767)}
      [:.container {:padding (px 16)}])
    (at-media {:min-width (px 768)}
      [:.container {:padding-left (px 16)
                    :padding-right (px 16)}])))

(def typography
  (list
    [:body {:font-family 'Roboto
            :font-size (px 18)
            :color (:dark theme/color)}]
    [:input {:color (:dark theme/color)}]
    [:.semi-transparent {:opacity 0.4}]
    [:.thin {:font-weight 300}]
    (at-media {:max-width (px 767)}
      [:body {:font-size (px 14)}])
    ))

(def reset
  (list
    [:body {:margin 0}]
    [:ul {:margin 0
          :padding 0
          :list-style 'none}]))

(def components
  (list
    [:.icon-lrg {:display "block"
                 :width (px 128)
                 :height (px 128)
                 }]
    [:.icon-fill {:fill (:dark theme/color)
                  :stroke 'none}]
    [:.icon-stroke {:stroke (:dark theme/color)
                    :fill 'transparent
                    :stroke-width (px 4)
                    :stroke-linecap 'round
                    :stroke-linejoin 'round}]
    [:.dropdown {:position 'absolute
                 :z-index 10
                 :width (px 256)}]
    [:.badge {:display 'inline-block
              :padding [[(px 4) (px 6)]]
              :font-size (px 10)
              :text-transform 'uppercase
              :background-color (rgba 0 0 0 0.1)}]
    [:.card {:background 'white
             :box-shadow [[0 (px 8) (px 8) (rgba 0 0 0 0.1)]]}
     [:&.shallow {:box-shadow [[0 (px 4) (px 4) (rgba 0 0 0 0.1)]]}]]
    [:.menu-item {:padding [[0 (px 12)]]
                  :height (px 36)
                  :cursor 'pointer
                  :display 'flex
                  :flex-direction 'row
                  :align-items 'center}
     [:&:hover {:background-color (rgba 0 0 0 0.05)}]
     [:&.active {:background-color (rgba 0 0 0 0.1)}]
     [:&.disabled {:color (rgba 0 0 0 0.3)}
      [:&:hover {:background-color 'transparent}]]]
    [:.toolbar {:height (px 56)}
     [:&>.container {:height (px 56)}]
     [:a {:color 'white
          :text-decoration 'none}]]
    [:.toolbar-lined {:border-bottom [[(px 1) 'solid (rgba 0 0 0 0.1)]]}]
    [:.toolbar-main {:background-color (:accent theme/color)
                     :color 'white
                     :border-bottom 'none}]))

(def buttons
  (list
    [:.button {:background-color 'white
               :color (:accent theme/color)
               :outline 'none
               :border-width (px 0)
               :border-radius (px 3)
               :font-size (px 16)
               :height (px 36)
               :padding [[(px 0) (px 12)]]
               :cursor 'pointer
               :white-space 'nowrap}
     [:&.toolbar-button {:height (px 56)
                         :border-radius 0}]
     [:&.lowercase {:text-transform 'lowercase}]
     [:&.transparent {:background-color 'transparent}
      [:&:hover {:background-color (rgba 0 0 0 0.05)}]
      [:&.active {:background-color (rgba 0 0 0 0.1)}]]
     [:&.outlined {:border [[(px 1) 'solid (:dark theme/color)]]
                   :color (:dark theme/color)}
      [:&.inverted {:border-color 'white
                    :color 'white}]]
     [:&.underlined {:box-shadow [[0 (px 2) 0 (:dark theme/color)]]
                     :border-radius 0
                     :background-color 'transparent}]
     [:&.accented {:background-color (:accent theme/color)
                   :color 'white}]
     [:&.darkened {:background-color (:dark theme/color)
                   :color 'white}]
     [:.icon-dropdown {:margin-right (px (- 6))}]]
    [:.button-group
     [:&.tight
      [:.button {:border-radius 0
                 :border-right-width 0}]
      [:.button:first-child {:border-top-left-radius (px 3)
                             :border-bottom-left-radius (px 3)}]
      [:.button:last-child {:border-top-right-radius (px 3)
                            :border-bottom-right-radius (px 3)
                            :border-right-width (px 1)}]]]))

(def base
  (list
    reset
    layout
    typography
    components
    buttons
    [:body {:background-color "#eee"}]))
