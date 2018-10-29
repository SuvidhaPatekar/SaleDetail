package itemsale.suvidha.com.itemsale.model

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import itemsale.suvidha.com.itemsale.model.dao.ItemDao
import itemsale.suvidha.com.itemsale.model.dao.SaleDao
import itemsale.suvidha.com.itemsale.model.entity.Item
import itemsale.suvidha.com.itemsale.model.entity.Sale

@Database(entities = [Item::class, Sale::class], version = 1, exportSchema = false)
abstract class SaleDatabase : RoomDatabase() {
  abstract fun itemDao(): ItemDao
  abstract fun purchaseDao(): SaleDao

  companion object {
    private const val DATABASE_NAME = "sales.db"
    private var INSTANCE: SaleDatabase? = null

    fun getInstance(context: Context): SaleDatabase {
      if (INSTANCE == null) {
        synchronized(SaleDatabase::class) {
          INSTANCE = Room.databaseBuilder(
              context,
              SaleDatabase::class.java, DATABASE_NAME
          )
              .fallbackToDestructiveMigration()
              .build()
        }
      }
      return INSTANCE!!
    }
  }
}