(def project 'dmeterfields)
(def version "0.1.0-SNAPSHOT")

(set-env!
  :resource-paths #{"resources"}
  :source-paths #{"src/cljs" "src/clj" "src/garden"}
  :dependencies
  '[[adzerk/boot-cljs "1.7.228-1" :scope "test"]
    [adzerk/boot-cljs-repl   "0.3.3" :scope "test"]
    [adzerk/boot-reload "0.4.11" :scope "test"]
    [com.cemerick/piggieback "0.2.1"  :scope "test"]
    [weasel                  "0.7.0"  :scope "test"]
    [pandeiro/boot-http "0.7.3" :scope "test"]

    ;; clojure
    [org.clojure/clojure "1.9.0-alpha14"]
    [org.clojure/clojurescript "1.9.293"]
    [org.clojure/core.async "0.2.385"]
    [org.clojure/test.check "0.9.0" :scope "test"]
    [org.clojure/tools.nrepl "0.2.12" :scope "test"]

    ;; styles
    [garden "1.3.0"]
    [org.martinklepsch/boot-garden "1.3.0-0"]
    [danielsz/boot-autoprefixer "0.0.8"]

    ;; server
    [hiccup "1.0.5"]
    #_[org.eclipse.jgit/org.eclipse.jgit.java7 "3.7.0.201502260915-r"
     :exclusions [com.jcraft/jsch]]
    #_[com.jcraft/jsch "0.1.52"]
    [clj-jgit "0.8.9"]

    ;; client
    [org.omcljs/om "1.0.0-alpha47"]
    [sablono "0.7.3"]
    [binaryage/devtools "0.8.1" :scope "test"]])


(require
  '[clojure.pprint :refer [pprint]]
  '[adzerk.boot-cljs      :refer [cljs]]
 '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
 '[adzerk.boot-reload    :refer [reload]]
 '[org.martinklepsch.boot-garden :refer [garden]]
 '[pandeiro.boot-http    :refer [serve]]
 '[danielsz.autoprefixer :refer [autoprefixer]]
 '[om-prerender.boot-om-prerender :refer [om-prerender]]
 '[om-style.boot-om-style :refer [om-style]]
 '[github-deploy.boot-github-deploy :refer [github-deploy]])

(deftask dev
  "Run app"
  []
  (comp
   (serve)
   (watch)
   (speak)
   (reload)
   (cljs-repl)
   (cljs :source-map true :optimizations :none)
   (garden :styles-var 'dmeterfields.styles/base
     :output-to "css/styles.css")
   (autoprefixer :files ["styles.css"])))

