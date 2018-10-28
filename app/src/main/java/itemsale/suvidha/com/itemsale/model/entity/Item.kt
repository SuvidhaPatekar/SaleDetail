package itemsale.suvidha.com.itemsale.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey

@Entity(
    tableName = "Item",
    foreignKeys = [ForeignKey(
        entity = Sale::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("purchaseId"),
        onDelete = CASCADE
    )]
)
data class Item(
  @PrimaryKey(autoGenerate = true) val id: Int,
  val itemName: String,
  val quantity: Int,
  val price: Double,
  val sgst: Double,
  val igst: Double,
  val totalPrice: Double,
  val purchaseId: Int
)