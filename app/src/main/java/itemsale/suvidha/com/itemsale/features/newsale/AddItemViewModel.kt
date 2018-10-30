package itemsale.suvidha.com.itemsale.features.newsale

import androidx.lifecycle.MutableLiveData
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
    val isNullName: Boolean,
    val isQuantityAdded: Boolean,
    val isPriceAdded: Boolean
  )

  init {
    viewState.value = ViewState(
        0, 0.0, 0.0, 0.0, 0.0, null, false
    )
    errorViewState.value = ErrorViewState(false, false, false)

  }

  private fun getCurrentViewState() = viewState.value!!
  private fun getCurrentErrorViewState() = errorViewState.value!!

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
      errorViewState.value = getCurrentErrorViewState().copy(
          isNullName = true, isQuantityAdded = false, isPriceAdded = false
      )
      return
    }

    if (getCurrentViewState().itemQuantity == 0L) {
      errorViewState.value = getCurrentErrorViewState().copy(
          isNullName = false, isQuantityAdded = true, isPriceAdded = false
      )
      return
    }

    if (getCurrentViewState().itemPrice == 0.0) {
      errorViewState.value = getCurrentErrorViewState().copy(
          isNullName = false, isQuantityAdded = false, isPriceAdded = true
      )
      return
    }


    errorViewState.value = getCurrentErrorViewState().copy(
        isNullName = false, isQuantityAdded = false, isPriceAdded = false
    )

    viewState.value = getCurrentViewState().copy(itemAdded = true)
  }
}