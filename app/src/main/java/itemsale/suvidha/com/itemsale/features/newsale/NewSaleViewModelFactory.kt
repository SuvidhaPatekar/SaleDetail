package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import itemsale.suvidha.com.itemsale.model.dao.SaleDao

internal class NewSaleViewModelFactory(private val saleDao: SaleDao) : ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(NewSaleViewModel::class.java)) {
      return modelClass.getConstructor(SaleDao::class.java)
          .newInstance(saleDao)
    }
    throw IllegalStateException("Unknown ViewModel class.")
  }
}
