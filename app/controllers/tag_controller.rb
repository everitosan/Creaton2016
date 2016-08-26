class TagController < ApplicationController

  def index
    tags = Tag.all()
    jsonRespose(tags, 200)
  end
end
