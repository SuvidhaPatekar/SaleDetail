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
        childColumns = arrayOf("saleId"),
        onDelete = CASCADE
    )]
)
data class Item(
  @PrimaryKey(autoGenerate = true) var id: Long = 0,
  val itemName: String,
  val quantity: Long,
  val price: Double,
  val sgst: Double,
  val igst: Double,
  val totalPrice: Double,
  val saleId: Long
)