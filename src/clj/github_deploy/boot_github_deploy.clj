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

(defn clean? [repo]
  (->> (git/git-status repo)
    vals (reduce set/union) empty?))

(deftask github-deploy
  []
  (let [repo (git/load-repo ".")
        working-branch (git/git-branch-current repo)
        latest-commit (first (git/git-log repo))
        status (git/git-status repo)]
    (if-not (clean? repo)
      (util/fail (str "Repo not clean: " status))
      (do
        (util/info "Repo is clean. Checking out master.")
        (git/git-checkout repo "master")
        (git/git-merge repo latest-commit)
        (if-not (clean? repo)
          (util/fail (str "Merge failed: " (git/git-status repo)))
          (do
            (util/info "Merged. Pushing")
            (with-programs [git]
              (git "push")
              (util/info "Pushed."))
            (if-not (clean? repo)
              (util/fail (str "Something went wrong. Repo is not clean."
                           (git/git-status repo)))
              (do
                (git/git-checkout repo working-branch)
                (util/info "Deploy successful.")))))))))

