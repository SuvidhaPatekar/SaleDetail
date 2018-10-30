package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiConsumer
import io.reactivex.schedulers.Schedulers
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
  private lateinit var sale: Sale

  data class ViewState(
    val items: List<Item>,
    val isPaid: Boolean,
    val totalQuantity: Long,
    val subTotal: Double,
    val isDone: Boolean,
    val isGreaterAmount: Boolean,
    val isNullCustomerName: Boolean,
    val isNullDate: Boolean,
    val isNullAmount: Boolean,
    val paidAmount: Double,
    val emptyItems: Boolean
  )

  init {
    viewState.value = ViewState(
        emptyList(), false, 0, 0.0, false, false, false, false, false, 0.0, false
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

    viewState.value =
        (ViewState(
            currentItems, getCurrentViewState().isPaid, currentQuantity, subTotal,
            getCurrentViewState().isDone, getCurrentViewState().isGreaterAmount,
            false, false,
            false, getCurrentViewState().paidAmount,
            false
        ))

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

    viewState.value =
        (ViewState(
            getCurrentViewState().items, isPaid,
            getCurrentViewState().totalQuantity, getCurrentViewState().subTotal,
            getCurrentViewState().isDone, isGreaterAmount, getCurrentViewState().isNullCustomerName,
            getCurrentViewState().isNullDate,
            getCurrentViewState().isNullAmount, amount, getCurrentViewState().emptyItems
        ))
  }

  fun addSale(
    customerName: String?,
    date: String?,
    paidAmount: String?
  ) {
    val state = getCurrentViewState()
    if (customerName.isNullOrEmpty()) {
      viewState.value = ViewState(
          state.items, state.isPaid, state.totalQuantity, state.subTotal, state.isDone,
          state.isGreaterAmount, true, state.isNullDate, state.isNullAmount, state.paidAmount,
          false
      )
      return
    }

    if (date.isNullOrEmpty()) {
      viewState.value = ViewState(
          state.items, state.isPaid, state.totalQuantity, state.subTotal, state.isDone,
          state.isGreaterAmount, false, true, false, state.paidAmount, false
      )
      return
    }


    if (getCurrentViewState().items.isEmpty()) {
      viewState.value = ViewState(
          state.items, state.isPaid, state.totalQuantity, state.subTotal, state.isDone,
          state.isGreaterAmount, false, false, false, state.paidAmount, true
      )
      return
    }


    if (paidAmount.isNullOrEmpty()) {
      viewState.value = ViewState(
          state.items, state.isPaid, state.totalQuantity, state.subTotal, state.isDone,
          state.isGreaterAmount, false, false, true, state.paidAmount, false
      )
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
            .subscribe(BiConsumer { id, throwable ->
              if (throwable != null) {
                //todo show toast
                return@BiConsumer
              }
            })
    )
    viewState.value =
        ViewState(emptyList(), false, 0, 0.0, true, false, false, false, false, 0.0, false)
  }
}