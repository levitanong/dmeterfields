(ns dmeterfields.data
  (:require
   [dmeterfields.theme :as theme]))

(def farm
  {:title "The Farm"
   ;; insert a map
   :content "Situated in San Simon, Pampanga, the D'Meter Fields Farm is dedicated to the breeding and fattening of cattle within the confines of a clean and bovine-friendly environment."
   :details [{:title "Australian Cattle"
              :content "The farm has a total land area of 30 hectares, and is home to a few thousand Brahmans imported live from Australia."
              :svg-id "icons.svg#australia"}
             {:title "Well Fed"
              :content "Our farm is located near ample and steady sources of forages and concentrate—perfect for our feed mill to produce feed our cattle find irresistible."
              :svg-id "icons.svg#forest"}
             {:title "Healthcare"
              :content "Our in-house veterinarian makes sure every animal on our farm is well taken care of, and as healthy as can be."
              :svg-id "icons.svg#stethoscope"}]})

#_{:title "Staff"
 :content "Our staff is well-trained, skilled, and disciplined. We can accept no less when it comes to the stewardship of our cattle."
 :svg-id "icons.svg#farmer"}

#_{:title "Fattening"
 :content "Our cattle is fattened in an enclosed space, giving the cattle maximum access to its rations."
   :svg-id "icons.svg#gauge"}

#_{:title "Silage"
 :content "Silages are stored in a concrete bunk house and green chops are produced by two 5-ton-per-hour choppers."
 :svg-id "icons.svg#barn"}

(def integration
  [#_{:title "Care"
    :content "We care about our animals, and we've go through great lengths to make sure they're healthy, happy, and well-fed."
    :bg-color (:tan theme/color)
    :details [{:title "Healthcare"
               :content "Our in-house veterinarian makes sure every animal on our farm is well taken care of, and as healthy as can be."
               :svg-id "icons.svg#stethoscope"}
              {:title "Delicious Food"
               :content "Our feed mill produces a premium mix of forage and grains that our cattle find irresistible."
               :svg-id "icons.svg#wheat"}
              {:title "Ample exercise"
               :content "We let the cattle roam the fields, enjoy the sunshine, and graze on grass."}]}
   {:title "Abattoir and Fabrication"
    :content "If it's a part of the animal, chances are, we can give it to you. Cut any way you like."
    :color (:light theme/color)
    :bg-color (:reddish theme/color)
    :details [{:title "Primals"
               :content "SSMPC's fabrication technicians extract pork, beef, and chicken primal cuts from the carcasses. You can be sure the cuts are subject to stringent quality control."
               :svg-id "icons.svg#quality"}
              {:title "Processed Food"
               :content "Want sausages, luncheon meats, and other processed meats? We have you covered."
               :svg-id "icons.svg#sausage"}
              {:title "Carcass"
               :content "For those who want to do their own processing, we can provide beef and hog carcasses, fresh or frozen."
               :pull-text "DIY"}]}
   {:title "Cold Facilities"
    :content "Good meat is not enough. We want good meat to stay good. SSMPC's storage and delivery facilities are unique in the Philippine market."
    :color (:dark-bluish theme/color)
    :bg-color (:light theme/color)
    :details [{:title "Cold Fabrication"
               :content "Even before meat processing starts, we're already ensuring the lifespan of our meat. All our fabrication and processing happens in sanitary chilled rooms."
               :svg-id "icons.svg#snowflake"}
              {:title "Cold Storage"
               :content "Carcasses are quickly cooled to 4°C and maintained, while primals are rapidly cooled to -20°C and kept at our cold storage at a minimum of -30°C."
               :svg-id "icons.svg#refrigerator"}
              {:title "Cold Delivery"
               :content "Finally, we use refrigerated vans to bridge the gap between our cold storage and your inventory."
               :svg-id "icons.svg#ice-truck"}]}])
