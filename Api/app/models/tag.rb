class Tag < ActiveRecord::Base
  has_and_belongs_to_many :histories

  validates :name,
            presence: true
end
