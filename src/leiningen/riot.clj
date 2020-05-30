(ns leiningen.riot
  (:require [leiningen.help :as help]
            [leiningen.core.main :as main]
            [leiningen.npm :refer [npm]]
            [cheshire.core :as json]
            [leiningen.npm.deps :refer [resolve-node-deps]]
            [leiningen.npm.process :refer [exec]]
            [robert.hooke]
            [leiningen.deps]
            [clojure.java.io :as io]))


(defn- riot-xtra-args
  [project]
  (let [riot-config (:riot project)
        compact     (if (:compact riot-config) ["--compact"] [])
        whitespace  (if (:whitespace riot-config) ["--whitespace"] [])
        expr        (if (:expr riot-config) ["--expr"] [])
        brackets    (if (:brackets riot-config) ["--brackets" (:brackets riot-config)] [])
        ext         (if (:ext riot-config) ["--ext" (:ext riot-config)] [])
        type        (if (:type riot-config) ["--type" (:type riot-config)] [])]
    (concat compact whitespace expr brackets ext type)))

(defn- run-riot-on
  [project full-cmd args]
  (leiningen.core.main/info "Invoking" full-cmd (clojure.string/join " " args))
  (exec (project :root) (cons full-cmd args)))

(defn- invoke
  [project & args]
  (let [local-riot (io/as-file "./node_modules/.bin/riot")
        _   (if-not (.exists local-riot) (npm project "install" "@riotjs/cli"))
        cmd (if (.exists local-riot) (.getPath local-riot) "riot")
        riot-config (:riot project)
        riot-opts (riot-xtra-args project)
        riot-deps (:tags riot-config)
        has-watch (some #(= % "watch") (flatten args))
        riot-opts (if has-watch (concat ["--watch"] riot-opts) riot-opts)
        riot-deps (if has-watch (subvec riot-deps 0 1) riot-deps)]


    (if has-watch
      (leiningen.core.main/info "Riot can only watch one location.  Watching" (first (first riot-deps))) nil)
    (mapv #(run-riot-on project cmd (concat riot-opts %)) riot-deps)
    ))

(defn riot
  "Invoke the Riot Compiler.
  Essentially, all the same options you can use with the Riot compiler should work
  for this plugin.

  :riot {
        :compact true
        :whitespace true
        :tags [
          [\"resources/public/\"]
          [\"resources/public/\" \"resources/public/all.js\"]
          [\"resources/public/my.tag\" \"resources/public/my.js\"]
        ]}
  "
  ([project & args]
   (cond (= ["help"] args)
         (do
           (println (help/help-for "riot"))
           (main/abort)
           )
         (= ["pprint"] args)
         (do
           (println (:riot project)))
         :else (invoke project args))))

