class LocationController < ApplicationController

  def search
    user_postion = toRadians(user_location_params)

    histories = History.includes(:tags).all
    near_histories = []

    for i in(0..( histories.length-1) )
      if get_distance( user_postion, toRadians(histories[i]) ) < 100
        logger.info "near"
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
      params.require(:user_location).permit(:longitude, :latitude)
    end
end
