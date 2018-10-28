package itemsale.suvidha.com.itemsale.model.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ForeignKey.CASCADE
import android.arch.persistence.room.PrimaryKey
import org.jetbrains.annotations.Nullable

@Entity(
    tableName = "Item",
    foreignKeys = [ForeignKey(
        entity = Purchase::class,
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