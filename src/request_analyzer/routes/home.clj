(ns request-analyzer.routes.home
  (:require [compojure.core :refer :all]
            [request-analyzer.views.layout :as layout]
            [hiccup.form :refer :all]
            [ring.middleware.multipart-params :as mp]
            [clojure.java.io :as io]
            [clojure-csv.core :as csv]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Templating ;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn home [& [file]]
  (layout/common [:h1 "Welcome Kepler Komet!"]
                 [:h2 "Please upload a file"]
                 [:p file]
                 (form-to {:enctype "multipart/form-data"}
                          [:post "/"]
                          (file-upload :file)
                          [:br]
                          (submit-button "Upload File"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; CSV Handler ;;;;;;;;;;;;;;;;;;;;;;;;;

(defn csv? [content-type]
  (= content-type "text/csv"))

(defn columns>1? [contents]
  (some #(> (count %) 1) contents))

(defn read-contents [file]
    (csv/parse-csv (slurp file)))

(defn handle-upload [params]
  (if (csv? (get-in params [:file :content-type]))
    (if (columns>1? (read-contents (get-in params [:file :tempfile])))
      (home "You have uploaded a file with multiple columns")
      (home "Success"))
    (home "Your file is not a csv")))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;; Routes ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defroutes home-routes
  (GET "/" [] (home))
  (POST "/" {params :params} (handle-upload params)))
