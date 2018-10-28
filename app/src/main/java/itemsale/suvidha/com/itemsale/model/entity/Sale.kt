package itemsale.suvidha.com.itemsale.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Sale(
  @PrimaryKey val id: Int,
  val customerName: String,
  val date: String,
  val paidAmount: Double,
  val isPaid: Boolean
)