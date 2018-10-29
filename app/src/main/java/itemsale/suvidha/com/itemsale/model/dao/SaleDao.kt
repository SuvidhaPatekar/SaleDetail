package itemsale.suvidha.com.itemsale.model.dao

import androidx.room.Dao
import androidx.room.Insert
import itemsale.suvidha.com.itemsale.model.entity.Sale

@Dao
interface SaleDao {

  @Insert
  fun insert(sale: Sale) : Long
}