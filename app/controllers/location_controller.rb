class LocationController < ApplicationController

  def search
    user_info = user_location_params

    if params[:user_info][:purged] == "1"
      histories = History.where('likes > 5').includes(:tags)
    else
      histories = History.includes(:tags).all
    end
    near_histories = []

    for i in(0..( histories.length-1) )
      distance = get_distance( user_info, histories[i] )
      logger.info "Distance to sounds #{distance}"
      if get_distance( user_info, histories[i] ) < 0.200 #distance set to less than 200 meters
        near_histories.push(histories[i])
      end
    end
    jsonRespose(near_histories.to_json( :include => [:tags] ), 200)
  end


  def get_distance(u_location, a_location)
    #convert to floats
    destiny = Hash.new
    destiny[:latitude] = a_location[:latitude].to_f
    destiny[:longitude] = a_location[:longitude].to_f

    u_location[:latitude] = u_location[:latitude].to_f
    u_location[:longitude] = u_location[:longitude].to_f

    # uses 's formula's formula
    earth_radius = 6371
    d_lon = u_location[:longitude] - destiny[:longitude]
    d_lat = u_location[:latitude] - destiny[:latitude]

    a = ( (Math::sin(d_lat/2) ** 2) + Math::cos( destiny[:latitude]) * Math::cos( u_location[:latitude]) * Math::sin(d_lon/2) ) ** 2
    c = 2 * Math::atan2( Math::sqrt(a), Math::sqrt(1-a) )
    d = earth_radius * c
    return c
  end

  private
    def user_location_params
      params.require(:user_info).permit(:longitude, :latitude, :purged)
    end
end
