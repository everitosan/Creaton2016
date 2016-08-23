require 'test_helper'

class LocationControllerTest < ActionController::TestCase
  # test "the truth" do
  #   assert true
  # end

  test "location distance"do
    post :get_distance

  end
end
