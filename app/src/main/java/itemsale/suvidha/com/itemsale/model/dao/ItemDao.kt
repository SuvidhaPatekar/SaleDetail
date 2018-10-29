package itemsale.suvidha.com.itemsale.model.dao

import androidx.room.Dao
import androidx.room.Insert
import itemsale.suvidha.com.itemsale.model.entity.Item

@Dao
interface ItemDao {

  @Insert fun addItem(item: Item)

  @Insert fun addItems(items: List<Item>)
}