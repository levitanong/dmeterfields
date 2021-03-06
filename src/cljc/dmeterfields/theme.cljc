(ns dmeterfields.theme
  (:require
   [garden.color :as gcolor]))

(def color
  {;;:accent (:avocado gcolor/color-name->hex)
   :dark "#013220" ;;"#4b2133"
   :light "#e4e1ea"
   :bluish "#92b7b3"
   :tan "#9d836a"
   :dark-bluish (gcolor/as-hex (gcolor/darken "#92b7b3" 40))
   :reddish (-> "#270815" (gcolor/lighten 20) gcolor/as-hex)
   :maroon "#4b2133"})
