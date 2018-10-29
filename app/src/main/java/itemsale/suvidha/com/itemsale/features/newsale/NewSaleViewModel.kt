package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.MutableLiveData
import itemsale.suvidha.com.itemsale.features.base.BaseViewModel
import itemsale.suvidha.com.itemsale.model.dao.SaleDao
import itemsale.suvidha.com.itemsale.model.entity.Item
import itemsale.suvidha.com.itemsale.model.entity.Sale
import itemsale.suvidha.com.itemsale.round2Decimal

class NewSaleViewModel(private val saleDao: SaleDao) : BaseViewModel() {

  val viewState: MutableLiveData<ViewState> = MutableLiveData()
  private lateinit var sale: Sale

  data class ViewState(
    val items: List<Item>,
    val isPaid: Boolean,
    val totalQuantity: Int,
    val subTotal: Double
  )

  init {
    viewState.value = ViewState(emptyList(), false, 0, 0.0)
  }

  fun getCurrentViewState() = viewState.value!!

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
        (ViewState(currentItems, getCurrentViewState().isPaid, currentQuantity, subTotal))
  }

  fun addSale(
    customerName: String,
    date: String,
    paidAmount: Double
  ) {
    sale = Sale(
        customerName = customerName, date = date, isPaid = getCurrentViewState().isPaid,
        paidAmount = paidAmount, id = -1
    )
    saleDao.insert(sale)
  }
}