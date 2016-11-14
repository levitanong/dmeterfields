(ns github-deploy.boot-github-deploy
  {:boot/export-tasks true}
  (:require
   [boot.core :as boot :refer [deftask]]
   [boot.util :as util]
   [clj-jgit.porcelain :as git]))

(def repo-states #{:added :changed :missing :modified :removed :untracked})

(deftask github-deploy
  []
  (let [repo (git/load-repo ".")
        current-branch (git/git-branch-current repo)
        status (git/git-status repo)
        clean? (every? (fn [state] (empty? (get status state)))
                 repo-states)]
    (if-not clean?
      (util/fail (str "Repo not clean: " status))
      (do
        (util/info "Repo is clean. Checking out master.")
        (git/git-checkout repo "master")
        (util/info (git/git-status repo))))
    ))

