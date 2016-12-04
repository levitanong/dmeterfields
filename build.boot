(def project 'dmeterfields)
(def version "0.1.0-SNAPSHOT")

(set-env!
  :resource-paths #{"resources"}
  :source-paths #{"src/cljc" "src/clj" "src/cljs"}
  :dependencies
  '[[adzerk/boot-cljs "1.7.228-2" :scope "test"]
    [adzerk/boot-cljs-repl   "0.3.3" :scope "test"]
    [adzerk/boot-reload "0.4.13" :scope "test"]
    [com.cemerick/piggieback "0.2.1"  :scope "test"]
    [weasel                  "0.7.0"  :scope "test"]
    [pandeiro/boot-http "0.7.3" :scope "test"]

    ;; clojure
    [org.clojure/clojure "1.9.0-alpha14"]
    [org.clojure/clojurescript "1.9.293"]
    ;; [org.clojure/core.async "0.2.385"]
    [org.clojure/test.check "0.9.0" :scope "test"]
    [org.clojure/tools.nrepl "0.2.12" :scope "test"]

    ;; styles
    [garden "1.3.0"]
    [org.martinklepsch/boot-garden "1.3.0-0"]
    [danielsz/boot-autoprefixer "0.0.8"]

    ;; server
    [hiccup "1.0.5" :scope "test"]
    [clj-jgit "0.8.9" :scope "test"]
    [me.raynes/conch "0.8.0" :scope "test"]
    [me.raynes/fs "1.4.6" :scope "test"]
    [om-style "0.0.1"]

    ;; client
    [org.omcljs/om "1.0.0-alpha47"]
    [binaryage/devtools "0.8.1" :scope "test"]])

(load-data-readers!)

(require
  '[adzerk.boot-cljs      :refer [cljs]]
  '[adzerk.boot-cljs-repl :refer [cljs-repl start-repl]]
  '[adzerk.boot-reload    :refer [reload]]
  '[org.martinklepsch.boot-garden :refer [garden]]
  '[pandeiro.boot-http    :refer [serve]]
  '[danielsz.autoprefixer :refer [autoprefixer]]
  '[dmeterfields.core]
  '[dmeterfields.styles]
  '[om-prerender.boot-om-prerender :refer [om-prerender]]
  '[om-style.boot-om-style :refer [om-style]]
  '[github-deploy.boot-github-deploy :refer [github-deploy]])

(deftask dev
  "Run app"
  []
  (comp
    (serve)
    (watch)
    (reload)
    (speak)
    (om-prerender
      :reconciler 'dmeterfields.core/reconciler
      :root 'dmeterfields.core/Root)
    (om-style
      :root 'dmeterfields.core/Root
      :base-style 'dmeterfields.styles/base
      :output-to "css/styles.css"
      :pretty-print true)
    (cljs-repl)
    (cljs
      :source-map true
      :optimizations :none
      :compiler-options {:preloads '[devtools.preload]})
    (autoprefixer :files ["styles.css"])))

(deftask prod
  "Prod"
  []
  (comp
    (om-prerender
      :reconciler 'dmeterfields.core/reconciler
      :root 'dmeterfields.core/Root)
    (cljs :optimizations :advanced)
    (om-style
      :root 'dmeterfields.core/Root
      :base-style 'dmeterfields.styles/base
      :output-to "css/styles.css"
      :pretty-print true)
    (autoprefixer :files ["styles.css"])
    (target)))

(deftask deploy
  "deploy"
  []
  (github-deploy))
