package itemsale.suvidha.com.itemsale.features.salesdetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import itemsale.suvidha.com.itemsale.model.dao.SaleDao

class SaleDetailViewModelFactory(private val saleDao: SaleDao) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaleDetailViewModel::class.java)) {
            return modelClass.getConstructor(SaleDao::class.java)
                .newInstance(saleDao)
        }
        throw IllegalStateException("Unknown ViewModel class.")
    }
}
