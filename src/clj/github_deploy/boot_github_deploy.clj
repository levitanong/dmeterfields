(ns github-deploy.boot-github-deploy
  {:boot/export-tasks true}
  (:require
   [boot.core :as boot :refer [deftask]]
   [boot.util :as util]
   [clj-jgit.porcelain :as git]
   [clj-jgit.querying :as git-query]
   [clojure.java.io :as io]
   [clojure.set :as set]
   [me.raynes.conch :refer [with-programs]]))

(def repo-states #{:added :changed :missing :modified :removed :untracked})

(deftask github-deploy
  []
  (let [repo (git/load-repo ".")
        current-branch (git/git-branch-current repo)
        latest-commit (first (git/git-log repo))
        status (git/git-status repo)
        clean? (->> status vals (reduce set/union) empty?)
        ]
    (if-not clean?
      (util/fail (str "Repo not clean: " status))
      (do
        (util/info "Repo is clean. Checking out master.")
        (git/git-checkout repo "master")
        (git/git-merge repo latest-commit)
        (util/info "Merged. Pushing")
        (with-programs [git]
          (git "push -u origin master"))
        #_(-> repo
          .push
          (.setRemote "origin")
          .call)
        (util/info "Pushed.")
        ))
    ))

