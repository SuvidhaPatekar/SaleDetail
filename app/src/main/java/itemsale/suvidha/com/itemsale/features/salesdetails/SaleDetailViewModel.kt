package itemsale.suvidha.com.itemsale.features.salesdetails

import androidx.lifecycle.MutableLiveData
import itemsale.suvidha.com.itemsale.features.base.BaseViewModel
import itemsale.suvidha.com.itemsale.model.dao.SaleDao
import itemsale.suvidha.com.itemsale.model.entity.Sale

class SaleDetailViewModel(private val saleDao: SaleDao) : BaseViewModel() {

  val viewState: MutableLiveData<ViewState> = MutableLiveData()

  data class ViewState(
    val sales: List<Sale>
  )

  init {
    viewState.value = ViewState(emptyList())
  }

  fun getCurrentViewState() = viewState.value!!

  fun delete() {

  }

  fun updatePayment() {

  }
}
