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

  @Query("SELECT * FROM sale where isPaid = :paid order by id DESC")
  fun getAll(paid: Int): Flowable<List<Sale>>

  @Query("DELETE FROM sale WHERE id = :id")
  fun deleteById(id: Long)

  @Query(
      "Update sale SET balanceAmount = :balanceAmount, paidAmount = :paidAmount, isPaid = :isPaid WHERE id = :id"
  )
  fun updateBalanceAmount(
    id: Long,
    balanceAmount: Double,
    paidAmount: Double,
    isPaid: Int
  )
}