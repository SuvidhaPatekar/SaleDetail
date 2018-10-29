package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.MutableLiveData
import itemsale.suvidha.com.itemsale.features.base.BaseViewModel
import itemsale.suvidha.com.itemsale.model.dao.ItemDao
import itemsale.suvidha.com.itemsale.round2Decimal

class AddItemViewModel(private val itemDao: ItemDao) : BaseViewModel() {

  val viewState: MutableLiveData<ViewState> = MutableLiveData()

  data class ViewState(
    val itemQuantity: Int,
    val itemPrice: Double,
    val totalPrice: Double,
    val tax: Double,
    val amount: Double,
    val itemName: String?,
    val itemAdded: Boolean,
    val isNullName: Boolean,
    val isQuantityAdded: Boolean,
    val isPriceAdded: Boolean
  )

  init {
    viewState.value = ViewState(0, 0.0, 0.0, 0.0, 0.0, null, false, false, false, false)
  }

  fun getCurrentViewState() = viewState.value!!

  fun addQuantity(quantity: Int) {
    calculateTotal(quantity, getCurrentViewState().itemPrice)
  }

  fun addPrice(price: Double) {
    calculateTotal(getCurrentViewState().itemQuantity, price)
  }

  private fun calculateTotal(
    quantity: Int,
    price: Double
  ) {
    val amount =
      (quantity * price).round2Decimal()
    val tax = (amount * 0.09).round2Decimal()
    val totalPrice = (amount + tax + tax).round2Decimal()

    viewState.value = ViewState(
        quantity, price,
        totalPrice,
        tax, getCurrentViewState().amount, getCurrentViewState().itemName,
        getCurrentViewState().itemAdded, false, false, false
    )
  }

  fun addItem(itemName: String?) {
    if (itemName.isNullOrEmpty()) {
      viewState.value = ViewState(
          getCurrentViewState().itemQuantity, getCurrentViewState().itemPrice,
          getCurrentViewState().totalPrice,
          getCurrentViewState().tax, getCurrentViewState().amount, getCurrentViewState().itemName,
          getCurrentViewState().itemAdded, true, false, false
      )
      return
    }

    if (getCurrentViewState().itemQuantity == 0) {
      viewState.value = ViewState(
          getCurrentViewState().itemQuantity, getCurrentViewState().itemPrice,
          getCurrentViewState().totalPrice,
          getCurrentViewState().tax, getCurrentViewState().amount, getCurrentViewState().itemName,
          getCurrentViewState().itemAdded, false, true, false
      )
      return
    }

    if (getCurrentViewState().itemPrice == 0.0) {
      viewState.value = ViewState(
          getCurrentViewState().itemQuantity, getCurrentViewState().itemPrice,
          getCurrentViewState().totalPrice,
          getCurrentViewState().tax, getCurrentViewState().amount, getCurrentViewState().itemName,
          getCurrentViewState().itemAdded, false, false, true
      )
      return
    }

    viewState.value = ViewState(
        getCurrentViewState().itemQuantity, getCurrentViewState().itemPrice,
        getCurrentViewState().totalPrice,
        getCurrentViewState().tax, getCurrentViewState().amount, getCurrentViewState().itemName,
        true, false, false, false
    )

  }
}