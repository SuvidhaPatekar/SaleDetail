package itemsale.suvidha.com.itemsale.features.salesdetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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

  fun getAll() {
    Log.d("get all", "inside get all")
    addDisposable(

        saleDao.getAll()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ it ->
              viewState.value = getCurrentViewState().copy(sales = it)
            }, { it ->
              it.printStackTrace()
            })
    )
  }
}
