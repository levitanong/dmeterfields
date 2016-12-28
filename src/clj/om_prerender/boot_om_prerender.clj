(ns om-prerender.boot-om-prerender
  {:boot/export-tasks true}
  (:require
   [boot.core :as boot :refer [deftask]]
   [boot.pod :as pod]
   [boot.util :as util]
   [clojure.java.io :as io]
   [hiccup.page :as hiccup]
   [om.dom :as dom]
   [om.next :as om]))

(defn html-wrapper [html-string]
  (hiccup/html5
    [:head
     [:meta {:charset "utf-8"}]
     [:meta {:http-equiv "X-UA-Compatible"
             :content "IE=edge"}]
     [:meta {:name "viewport"
             :content "width=device-width, initial-scale=1"}]
     [:title "D'Meter Fields"]
     (hiccup/include-css "css/styles.css")
     ;; replace | with %7C
     (hiccup/include-css "https://fonts.googleapis.com/css?family=Abel%7CPacifico%7CSource+Sans+Pro:300,400,600")]
    [:body
     [:div#app html-string]
     (hiccup/include-js "/main.js")]))

(defn- render-to-file!
  "Renders to file.
  reconciler and component are resolved here to refresh them each time they are changed."
  [out-file reconciler component]
  (let [r (some-> reconciler resolve var-get)
        c* (some-> component resolve var-get)
        c (om/add-root! r c* nil)
        html-string (dom/render-to-str c)]
    (doto out-file
      io/make-parents
      (spit (html-wrapper html-string)))))

(defn ns-tracker-pod []
  (->> '[[ns-tracker "0.3.0"] [org.clojure/tools.namespace "0.2.11"]]
    (assoc (boot/get-env) :dependencies)
    pod/make-pod))

(deftask om-prerender
  "Prerender frontend UI to index.html"
  [r reconciler SYM sym "The reconciler."
   c root SYM sym "The root component."]
  (let [tmp (boot/tmp-dir!)
        src-paths (vec (boot/get-env :source-paths))
        ns-pod (ns-tracker-pod)]
    (pod/with-eval-in ns-pod
      (require 'ns-tracker.core)
      (def cns (ns-tracker.core/ns-tracker ~src-paths)))
    (boot/with-pre-wrap fileset
      (let [changed-ns (pod/with-eval-in ns-pod (cns))]
        (doseq [n changed-ns]
          (require n :reload))
        (boot/empty-dir! tmp)
        (util/info "Prerendering...\n")
        (let [out-file (io/file tmp "index.html")]
          (render-to-file! out-file
            reconciler root))
        (util/info "Prerendering complete.\n")
        (-> fileset
          (boot/add-resource tmp)
          boot/commit!)))))
