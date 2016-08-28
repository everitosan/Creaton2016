class TagController < ApplicationController

  def index
    tags = Tag.all()
    if tags.size == 0
      t = Tag.create("name"=>"Mitos Urbanos")
      t = Tag.create("name"=>"Amor")
      t = Tag.create("name"=>"Crimen")
      t = Tag.create("name"=>"Mitos Urbanos")
      tags = Tag.all()
    jsonRespose(tags, 200)
  end
end
