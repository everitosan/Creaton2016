class History < ActiveRecord::Base
  has_and_belongs_to_many :tags

  validates :name,
            presence: true

  validates :latitude,
            presence: true

  validates :longitude,
            presence: true

  validates :url,
            presence: true,
            uniqueness: true

  validates :likes,
            presence: true,
            numericality: true
end
