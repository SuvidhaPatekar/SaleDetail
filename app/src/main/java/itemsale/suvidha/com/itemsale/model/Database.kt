package itemsale.suvidha.com.itemsale.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import itemsale.suvidha.com.itemsale.model.dao.ItemDao
import itemsale.suvidha.com.itemsale.model.entity.Item

@Database(entities = arrayOf(Item::class), version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {
  abstract fun itemDao(): ItemDao
}