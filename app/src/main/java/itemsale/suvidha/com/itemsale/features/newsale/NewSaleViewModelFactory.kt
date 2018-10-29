package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import itemsale.suvidha.com.itemsale.model.dao.ItemDao
import itemsale.suvidha.com.itemsale.model.dao.SaleDao

internal class NewSaleViewModelFactory(
  private val saleDao: SaleDao,
  private val itemDao: ItemDao
) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(NewSaleViewModel::class.java)) {
      return modelClass.getConstructor(SaleDao::class.java, ItemDao::class.java)
          .newInstance(saleDao, itemDao)
    }
    throw IllegalStateException("Unknown ViewModel class.")
  }
}
