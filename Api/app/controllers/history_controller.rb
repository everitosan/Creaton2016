class HistoryController < ApplicationController
  before_action :set_default_response_format
   skip_before_filter :verify_authenticity_token

  def index
    histories = History.all
    jsonRespose(histories, 200)
  end

  def create
    file = params[:history][:audio] #recover file to upload
    if !file.nil?
      m_params = params
      m_params.delete("audio") #delete uncessary hash

      file_name = Time.now.strftime("%Y_%d_%m_%H_%M_%S-%Z") + file.original_filename
      file_path = Rails.root.join('public', 'uploads', file_name)

      m_params["history"]["url"] = file_path.to_s #asign url to hash object

      if upload(file, file_path)
        history = History.new(history_params(m_params))
        if history.save
          jsonRespose(history, 200)
        else
          jsonRespose(history.errors, 400)
        end
      end
    else
       jsonRespose(file, 500)
    end

  end

  def searchByLocation
  end

  def show
  end

  def destroy
  end


  def jsonRespose(object, status)
    respond_to do |format|
        format.json { render json: object ,  status: status}
    end
  end

  private
    def history_params(m_params)
        m_params.require(:history).permit(:name, :location, :url)
    end

    def upload (history_audio, file_path)
      history_audio_filename =  history_audio.original_filename
      File.open(file_path, 'wb') do |file|
        file.write(history_audio.read)
      end
    end

  protected
    def set_default_response_format
      request.format = :json
    end

end
