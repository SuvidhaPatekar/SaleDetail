package itemsale.suvidha.com.itemsale.model.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import itemsale.suvidha.com.itemsale.model.entity.Item

@Dao
interface ItemDao {

  @Insert fun addItem(item: Item)
}