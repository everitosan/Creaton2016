require 'test_helper'

class HistoryControllerTest < ActionController::TestCase
  # test "the truth" do
  #   assert true
  # end

  test "history index" do
    get :index
    assert_response :success
  end
  
end
