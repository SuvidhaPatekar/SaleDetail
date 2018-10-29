package itemsale.suvidha.com.itemsale.model.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

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