(ns wareblog.atom-storage
  (require [com.stuartsierra.component :as component]
           [taoensso.timbre :as timbre]
           [wareblog.article-storage :as storage]
           ))

;Provide alias for logging with timbre
(timbre/refer-timbre)

(def some-edn-string
  {:header "\"My first experience with edn. A format for data exchange.\""
   :created-on "#inst \"2015-05-05T21:56:00Z\""
   :created-by "#wareblog/author {:given-name \"Matthias\" :name \"Sattel\"}"
   :abstract "#wareblog/abstract [\"This is the very first time that I am using \" #wareblog/abbreviation :edn \". In normal situation I would simply use an existing solution for markup of a blog, because there are already good solutions out there, e.g. \" #wareblog/abbreviation :md \". But in this case I want to learn new stuff and thus I will reinvent the wheel.\"]"
   :content "#wareblog/content [#wareblog/paragraph [\"Starting with something new is exciting, but can turn soon into frustration. Let's see how it works with \" #wareblog/abbreviation :edn]
   #wareblog/paragraph [\"The goal of my little project is to use \" #wareblog/abbreviation :edn \" for markup. I will use it to store articles and then render it to html using custom edn-readers.\"]]"})

(def articles
  (atom {:abc some-edn-string}))

(defrecord Storage [state]

  component/Lifecycle

  (start [this]
    (do
      (info "Starting storage test component")
      (assoc this :state "started")
    ))

  (stop [this]
    (do
      (info "Stopping storage test component")
      (info "State of the storage component " state)
      this
    ))

  storage/Article-Storage

  (get-by-id [this id]
    (id @articles) 
    )
  )

(defn new-storage []
  (map->Storage {}))
 
