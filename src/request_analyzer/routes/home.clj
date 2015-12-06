(ns request-analyzer.routes.home
  (:require [compojure.core :refer :all]
            [request-analyzer.views.layout :as layout]
            [hiccup.form :refer :all]
            [ring.middleware.multipart-params :as mp]
            [clojure.java.io :as io]
            [clojure-csv.core :as csv]))

(defn home [& [file]]
  (layout/common [:h1 "Welcome Kepler Komet!"]
                 [:h2 "Please upload a file"]
                 [:p file]
                 (form-to {:enctype "multipart/form-data"}
                          [:post "/"]
                          (file-upload :file)
                          [:br]
                          (submit-button "Upload File"))))

(defn read-contents [file] 
  (with-open [in-file (io/reader file)]
    (println
     (csv/parse-csv in-file))))
  

(defn handle-upload [params]
  (println params)
  (home "success"))

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" {params :params} (handle-upload params) (read-contents (get params :tempfile))))
