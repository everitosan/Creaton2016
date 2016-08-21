class ApplicationController < ActionController::Base
  # Prevent CSRF attacks by raising an exception.
  # For APIs, you may want to use :null_session instead.
  protect_from_forgery with: :null_session

  before_action :set_default_response_format


  def jsonRespose(object, status)
    respond_to do |format|
        format.json { render json: object ,  status: status}
    end
  end
  
  protected
    def set_default_response_format
      request.format = :json
    end
end
