package itemsale.suvidha.com.itemsale.model

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import itemsale.suvidha.com.itemsale.model.dao.ItemDao
import itemsale.suvidha.com.itemsale.model.dao.PurchaseDao
import itemsale.suvidha.com.itemsale.model.entity.Item
import itemsale.suvidha.com.itemsale.model.entity.Purchase

@Database(entities = [Item::class, Purchase::class], version = 1, exportSchema = false)
abstract class ItemDatabase : RoomDatabase() {
  abstract fun itemDao(): ItemDao
  abstract fun purchaseDao(): PurchaseDao
}