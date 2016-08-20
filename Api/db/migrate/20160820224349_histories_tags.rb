class HistoriesTags < ActiveRecord::Migration
  def change
    create_table :histories_tags, index: false do |t|
      t.belongs_to :tag
      t.belongs_to :history
    end
  end
end
