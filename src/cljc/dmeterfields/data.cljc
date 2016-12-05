(ns dmeterfields.data)

(def farm
  {:title "The Farm"
   ;; insert a map
   :content "Situated in San Simon, Pampanga, the D'Meter Fields Farm is dedicated to the breeding and fattening of cattle within the confines of a clean and bovine-friendly environment."
   :details [{:title "Australian Cattle"
              :content "The farm has a total land area of 30 hectares, and is home to 5,800 Brahmans all imported live from Australia."
              :svg-id "icons.svg#australia"}
             {:title "Strategic Location"
              :content "Strategically located near ample and steady sources of forages and concentrate, we're confident we've chosen the right home for our herd. "
              :svg-id "icons.svg#map"}
             {:title "Healthcare"
              :content "Our in-house veterinarian makes sure every animal on our farm is well taken care of, and as healthy as can be."
              :svg-id "icons.svg#stethoscope"}]})

(def integration
  [{:title "Feed Lot Techniques"
    :content "We aren't afraid of learning. Our techniques are adapted from the advice of experts from all around the globe."
    :details [{:title "Fattening"
               :content "Our cattle is fattened in an enclosed space, giving the cattle maximum access to its rations."
               :svg-id "icons.svg#gauge"}
              {:title "Silage"
               :content "Silages are stored in a 4,200 square meter concrete warehouse and green chops are produced by two 5-ton-per-hour choppers."
               :svg-id "icons.svg#barn"}
              {:title "Premium Mix"
               :content "A premium mix of forage and grains produced from D’Meter’s own mixing facilities help the cattle produce excellent quality meat."
               :svg-id "icons.svg#wheat"}]}
   {:title "Abattoir and Fabrication"
    :content "If it's a part of the animal, chances are, we can give it to you. Cut any way you like it."
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
    :details [{:title "Cold Fabrication"
               :content "Even before meat processing starts, we're already ensuring the lifespan of our meat. All our fabrication and processing happens in sanitary cold (10°C) rooms."}
              {:title "Cold Storage"
               :content "All our products, be they primals or carcasses, are rapidly cooled from 10°C to -20°C to ensure no ice crystals form. These are then stored at -10°C."}
              {:title "Cold Delivery"
               :content "Finally, we use refrigerated vans to bridge the gap between our cold storage and your inventory."}]}])
