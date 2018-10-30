package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.MutableLiveData
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.features.base.BaseViewModel
import itemsale.suvidha.com.itemsale.round2Decimal

class AddItemViewModel : BaseViewModel() {

  val viewState: MutableLiveData<ViewState> = MutableLiveData()
  val errorViewState: MutableLiveData<ErrorViewState> = MutableLiveData()

  data class ViewState(
    val itemQuantity: Long,
    val itemPrice: Double,
    val totalPrice: Double,
    val tax: Double,
    val amount: Double,
    val itemName: String?,
    val itemAdded: Boolean
  )

  data class ErrorViewState(
    val error: Int
  )

  init {
    viewState.value = ViewState(
        0, 0.0, 0.0, 0.0, 0.0, null, false
    )
    errorViewState.value = ErrorViewState(-1)

  }

  private fun getCurrentViewState() = viewState.value!!

  fun addQuantity(quantity: Long) {
    calculateTotal(quantity, getCurrentViewState().itemPrice)
  }

  fun addPrice(price: Double) {
    calculateTotal(getCurrentViewState().itemQuantity, price)
  }

  private fun calculateTotal(
    quantity: Long,
    price: Double
  ) {
    val amount =
      (quantity * price).round2Decimal()
    val tax = (amount * 0.09).round2Decimal()
    val totalPrice = (amount + tax + tax).round2Decimal()


    viewState.value = getCurrentViewState().copy(
        itemQuantity = quantity, itemPrice = price,
        totalPrice = totalPrice,
        tax = tax
    )
  }

  fun addItem(itemName: String?) {
    if (itemName.isNullOrEmpty()) {
      errorViewState.value = ErrorViewState(R.string.enter_item_name)
      return
    }

    if (getCurrentViewState().itemQuantity == 0L) {
      errorViewState.value = ErrorViewState(R.string.enter_item_quantity)
      return
    }

    if (getCurrentViewState().itemPrice == 0.0) {
      errorViewState.value = ErrorViewState(R.string.enter_item_rate)
      return
    }
    errorViewState.value = ErrorViewState(-1)

    viewState.value = getCurrentViewState().copy(itemAdded = true)
  }
}