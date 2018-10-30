package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiConsumer
import io.reactivex.schedulers.Schedulers
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.features.base.BaseViewModel
import itemsale.suvidha.com.itemsale.model.dao.ItemDao
import itemsale.suvidha.com.itemsale.model.dao.SaleDao
import itemsale.suvidha.com.itemsale.model.entity.Item
import itemsale.suvidha.com.itemsale.model.entity.Sale
import itemsale.suvidha.com.itemsale.round2Decimal

class NewSaleViewModel(
  private val saleDao: SaleDao,
  private val itemDao: ItemDao
) : BaseViewModel() {

  val viewState: MutableLiveData<ViewState> = MutableLiveData()
  val errorViewState: MutableLiveData<ErrorViewState> = MutableLiveData()

  private lateinit var sale: Sale

  data class ViewState(
    val items: List<Item>,
    val isPaid: Boolean,
    val totalQuantity: Long,
    val subTotal: Double,
    val isDone: Boolean,
    val paidAmount: Double
  )

  data class ErrorViewState(
    val error: Int
  )

  init {
    viewState.value = ViewState(
        emptyList(), false, 0, 0.0, false, 0.0
    )

    errorViewState.value = ErrorViewState(
        -1
    )
  }

  private fun getCurrentViewState() = viewState.value!!

  fun addItem(item: Item) {
    val currentViewState = getCurrentViewState()
    val currentItems = mutableListOf<Item>()
    currentItems.addAll(currentViewState.items)
    currentItems.add(0, item)

    var currentQuantity = currentViewState.totalQuantity
    currentQuantity += item.quantity
    var subTotal = currentViewState.subTotal
    subTotal = (subTotal + item.totalPrice).round2Decimal()

    viewState.value = getCurrentViewState().copy(
        items = currentItems, totalQuantity = currentQuantity, subTotal = subTotal
    )

    checkPaidAmount(getCurrentViewState().paidAmount)
  }

  fun checkPaidAmount(amount: Double) {
    val isPaid: Boolean
    val isGreaterAmount: Boolean
    when {

      amount == getCurrentViewState().subTotal -> {
        isGreaterAmount = false
        isPaid = true
      }
      amount < getCurrentViewState().subTotal -> {
        isGreaterAmount = false
        isPaid = false
      }
      else -> {
        isPaid = false
        isGreaterAmount = true
      }
    }

    viewState.value = getCurrentViewState().copy(isPaid = isPaid, paidAmount = amount)
    if (isGreaterAmount) {
      errorViewState.value = ErrorViewState(R.string.err_greater_amount)
    }
  }

  fun addSale(
    customerName: String?,
    date: String?,
    paidAmount: String?
  ) {
    val state = getCurrentViewState()
    if (customerName.isNullOrEmpty()) {
      errorViewState.value = ErrorViewState(R.string.err_name)
      return
    }

    if (date.isNullOrEmpty()) {
      errorViewState.value = ErrorViewState(R.string.err_date)
      return
    }


    if (getCurrentViewState().items.isEmpty()) {
      errorViewState.value = ErrorViewState(R.string.err_items)
      return
    }


    if (paidAmount.isNullOrEmpty()) {
      errorViewState.value = ErrorViewState(R.string.err_amount)
      return
    }

    val balanceAmount =
      (getCurrentViewState().subTotal - getCurrentViewState().paidAmount).round2Decimal()

    sale = Sale(
        customerName = customerName!!, date = date!!, isPaid = state.isPaid,
        paidAmount = paidAmount!!.toDouble(),
        balanceAmount = balanceAmount, totalAmount = getCurrentViewState().subTotal
    )

    addDisposable(
        Single.fromCallable {
          return@fromCallable saleDao.insert(sale)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BiConsumer { id, throwable ->
              if (throwable != null) {
                throwable.printStackTrace()
                return@BiConsumer
              }
              addItemsInDbForSale(id)
            })
    )
  }

  private fun addItemsInDbForSale(saleId: Long) {
    val items = getCurrentViewState().items

    val itemsTobeInserted = mutableListOf<Item>()
    items.forEach {
      itemsTobeInserted.add(it.copy(saleId = saleId))
    }

    addDisposable(
        Single.fromCallable {
          return@fromCallable itemDao.addItems(itemsTobeInserted)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(BiConsumer { _, throwable ->
              if (throwable != null) {
                return@BiConsumer
              }
            })
    )
    viewState.value = getCurrentViewState().copy(isDone = true)
    errorViewState.value = ErrorViewState(-1)

  }
}