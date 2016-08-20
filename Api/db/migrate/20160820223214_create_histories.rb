class CreateHistories < ActiveRecord::Migration
  def change
    create_table :histories do |t|
      t.string :name
      t.string :location
      t.string :url
      t.integer :likes, :default => 0

      t.timestamps null: false
    end
  end
end
