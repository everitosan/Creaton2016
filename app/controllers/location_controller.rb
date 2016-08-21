class LocationController < ApplicationController

  def search
    user_info = toRadians(user_location_params)

    if params[:user_info][:purged] == "1"
      histories = History.where('likes > 5').includes(:tags)
    else
      histories = History.includes(:tags).all
    end
    near_histories = []

    for i in(0..( histories.length-1) )
      if get_distance( user_info, toRadians(histories[i]) ) < 100
        near_histories.push(histories[i])
      end
    end
    jsonRespose(near_histories.to_json( :include => [:tags] ), 200)
  end

  def toRadians (location)

    radians = Hash.new
    radians[:latitude] = (location["latitude"].to_f * Math::PI)/180;
    radians[:longitude] = (location["longitude"].to_f * Math::PI)/180;

    return radians
  end

  def get_distance(u_location, a_location)
    earth_radius = 6371
    return (Math::acos(Math::sin(u_location[:latitude]) * Math::sin(a_location[:latitude]) + Math::cos(u_location[:latitude]) * Math::cos(a_location[:latitude]) * Math::cos(u_location[:longitude]- a_location[:longitude] )) )* earth_radius;
  end

  private
    def user_location_params
      params.require(:user_info).permit(:longitude, :latitude, :purged)
    end
end
