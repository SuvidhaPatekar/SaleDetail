package itemsale.suvidha.com.itemsale.features.salesdetails

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

    private fun getCurrentViewState() = viewState.value!!

    fun delete(
        id: Long
    ) {
        addDisposable(
            Completable.fromCallable {
                return@fromCallable saleDao.deleteById(id)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                }
        )
    }

    fun updatePayment(
        id: Long,
        balanceAmount: Double,
        paidAmount: Double,
        isPaid: Int
    ) {

        addDisposable(
            Completable.fromCallable {
                return@fromCallable saleDao.updateBalanceAmount(
                    id, balanceAmount, paidAmount, isPaid
                )
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                }
        )
    }

    fun getAll(paid: Int) {
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
