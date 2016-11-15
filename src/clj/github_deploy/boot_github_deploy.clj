(ns github-deploy.boot-github-deploy
  {:boot/export-tasks true}
  (:require
   [boot.core :as boot :refer [deftask]]
   [boot.task.built-in :refer [sift]]
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
    (if (= "master" working-branch)
      (util/fail "Currently on master. Please switch to your working branch.\n")
      (if-not (clean? repo)
        (util/fail (str "Repo not clean: " status "\n"))
        (do
          (util/info "Repo is clean. Checking out master. \n")
          (git/git-checkout repo "master")
          (git/git-merge repo latest-commit)
          (if-not (clean? repo)
            (util/fail (str "Merge failed: " (git/git-status repo) "\n"))
            (do
              (util/info "Merged. Moving contents of /target to /\n")
              (with-programs [cp git]
                (cp "-r" "-f" "./target/index.html" "./")
                (git "add" "--all"))
              #_(with-programs [git]
                  (git "push")
                  (util/info "Pushed.\n"))
              #_(if-not (clean? repo)
                  (util/fail (str "Something went wrong. Repo is not clean.\n"
                               (git/git-status repo)))
                  (do
                    (git/git-checkout repo working-branch)
                    (util/info "Deploy successful.\n"))))))))))

