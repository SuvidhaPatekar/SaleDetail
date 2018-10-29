package itemsale.suvidha.com.itemsale.features.salesdetails

import android.util.Log
import androidx.lifecycle.MutableLiveData
import io.reactivex.Completable
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

  fun delete(
    id: Long,
    paid: Int
  ) {
    addDisposable(
        Completable.fromCallable {
          return@fromCallable saleDao.deleteById(id)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
              getAll(paid)
            }
    )
  }

  fun updatePayment(
    id: Long,
    paid: Int
  ) {
    addDisposable(
        Completable.fromCallable {
          return@fromCallable saleDao.updateBalanceAmount(id, 0.0)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
              getAll(paid)
            }
    )
  }

  fun getAll(paid: Int) {
    Log.d("get all", "inside get all")
    addDisposable(
        saleDao.getAll(paid)
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
