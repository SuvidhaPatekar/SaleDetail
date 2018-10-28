package itemsale.suvidha.com.itemsale.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Item(
  @PrimaryKey val id: Int,
  val itemName: String,
  val quantity: Int,
  val price: Double,
  val sgst: Double,
  val igst: Double,
  val totalPrice: Double
)