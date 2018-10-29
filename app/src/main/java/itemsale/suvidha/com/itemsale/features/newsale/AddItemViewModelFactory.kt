package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import itemsale.suvidha.com.itemsale.model.dao.ItemDao

class AddItemViewModelFactory(private val itemDao: ItemDao) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(AddItemViewModel::class.java)) {
      return modelClass.getConstructor(ItemDao::class.java)
          .newInstance(itemDao)
    }
    throw IllegalStateException("Unknown ViewModel class.")
  }
}
