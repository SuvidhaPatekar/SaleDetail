package itemsale.suvidha.com.itemsale.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Sale(
  @PrimaryKey val id: Int,
  val customerName: String,
  val date: String,
  val paidAmount: Double,
  val isPaid: Boolean
)