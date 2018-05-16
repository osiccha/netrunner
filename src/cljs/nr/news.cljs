(ns nr.news
  (:require-macros [cljs.core.async.macros :refer [go]])
  (:require [netrunner.ajax :refer [GET]]
            [netrunner.cardbrowser :refer [add-symbols] :as cb]
            [netrunner.ws :refer [ws-send!]]
            [reagent.core :as r]))

(def news-state (r/atom {}))

(go (swap! news-state assoc :news (:json (<! (GET "/data/news")))))

(defn news [news]
  [:div.news-box.panel.blue-shade
   [:ul.list
    (doall (for [d (:news @news)]
             [:li.news-item
              [:span.date (-> (:date d) js/Date. js/moment (.format "dddd MMM Do - HH:mm"))]
              [:span.title {:dangerouslySetInnerHTML #js {:__html (cb/add-symbols (js/marked (:title d)))}}]]))]])