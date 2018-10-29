package itemsale.suvidha.com.itemsale.model.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import io.reactivex.Flowable
import itemsale.suvidha.com.itemsale.model.entity.Sale

@Dao
interface SaleDao {

  @Insert
  fun insert(sale: Sale): Long

  @Query("SELECT * FROM sale")
  fun getAll(): Flowable<List<Sale>>
}