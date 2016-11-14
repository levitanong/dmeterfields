(ns github-deploy.boot-github-deploy
  {:boot/export-tasks true}
  (:require
   [boot.core :as boot :refer [deftask]]
   [clj-jgit.porcelain :as git]))

(deftask github-deploy
  []
  (let [repo (git/load-repo ".")
        current-branch (git/git-branch-current repo)]
    (println (git/git-status repo))))

