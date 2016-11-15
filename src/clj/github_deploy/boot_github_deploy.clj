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
   [me.raynes.conch :refer [with-programs]]
   [me.raynes.fs :as fs]))

(def repo-states #{:added :changed :missing :modified :removed :untracked})

(defn clean? [repo]
  (->> (git/git-status repo)
    vals (reduce set/union) empty?))

(deftask github-deploy
  [i ignore-regex MATCH #{regex} "The set of regexes to ignore"
   v verbose? bool "Verbose"]
  (let [repo (git/load-repo ".")
        working-branch (git/git-branch-current repo)
        latest-commit (first (git/git-log repo))
        status (git/git-status repo)
        verbose? (when (nil? verbose?) true)]
    (if (= "master" working-branch)
      (util/fail "Currently on master. Please switch to your working branch.\n")
      (do
        (util/info "Checking out master. \n")
        (git/git-checkout repo "master")
        (git/git-merge repo latest-commit)
        (if-not (clean? repo)
          (util/fail (str "Merge failed: " (git/git-status repo) "\n"))
          (do
            (util/info "Merged. Moving contents of /target to /\n")
            (let [included-files (remove #(re-find #"./target/main\.out" (.getPath %))
                                   (.listFiles (fs/file "./target")))]
              (doseq [file included-files]
                (let [dest (if (fs/directory? (.getPath file))
                             (fs/file "./") (fs/file "./" (.getName file)))]
                  (if (fs/directory? (.getPath file))
                    (fs/copy-dir file dest)
                    (fs/copy file dest))
                  (when verbose?
                    (util/info (str "Copied" (.getPath file) "to" dest "\n"))))))
            (with-programs [git]
              (git "add" "--all")
              (git/git-commit repo "Deploy")
              (git "push")
              (util/info "Pushed.\n")
              (if-not (clean? repo)
                (util/fail (str "Something went wrong. Repo is not clean.\n"
                             (git/git-status repo)))
                (do
                  (git/git-checkout repo working-branch)
                  (util/info "Deploy successful.\n"))))))))))

