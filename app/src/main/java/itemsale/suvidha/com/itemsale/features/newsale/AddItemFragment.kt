package itemsale.suvidha.com.itemsale.features.newsale

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.R.layout
import itemsale.suvidha.com.itemsale.model.SaleDatabase
import itemsale.suvidha.com.itemsale.model.entity.Item
import itemsale.suvidha.com.itemsale.round2Decimal
import kotlinx.android.synthetic.main.fragment_item_detail.btnDone
import kotlinx.android.synthetic.main.fragment_item_detail.etItemName
import kotlinx.android.synthetic.main.fragment_item_detail.etItemPrice
import kotlinx.android.synthetic.main.fragment_item_detail.etItemQuantity
import kotlinx.android.synthetic.main.fragment_item_detail.tvIgst
import kotlinx.android.synthetic.main.fragment_item_detail.tvSgst
import kotlinx.android.synthetic.main.fragment_item_detail.tvTotal

class AddItemFragment : DialogFragment() {
  private lateinit var listener: OnClickListener
  private var itemQuantity = 0
  private var itemPrice = 0.0
  private var totalPrice = 0.0
  private var tax = 0.0
  private var amount = 0.0
  private var itemName: String? = null

  private lateinit var viewModel: AddItemViewModel
  private lateinit var viewModelFactory: AddItemViewModelFactory

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layout.fragment_item_detail, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    btnDone.setOnClickListener {
      addItem()
    }
    calculateTotal()
    etItemQuantity.addTextChangedListener(
        object : TextWatcher {
          override fun afterTextChanged(quantity: Editable?) {
            itemQuantity = if (quantity.isNullOrEmpty()) {
              0
            } else {
              quantity.toString()
                  .toInt()
            }

            calculateTotal()
          }

          override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
          ) {

          }

          override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
          ) {

          }
        })

    etItemPrice.addTextChangedListener(
        object : TextWatcher {
          override fun afterTextChanged(price: Editable?) {
            itemPrice = if (price.isNullOrEmpty()) {
              0.0
            } else {
              price.toString()
                  .toDouble()
            }
            calculateTotal()
          }

          override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
          ) {
          }

          override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
          ) {
          }
        })

    etItemName.addTextChangedListener(
        object : TextWatcher {
          override fun afterTextChanged(itemNameEt: Editable?) {
            if (!itemNameEt.isNullOrEmpty()) {
              itemName = itemNameEt.toString()
            }
          }

          override fun beforeTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
          ) {
          }

          override fun onTextChanged(
            p0: CharSequence?,
            p1: Int,
            p2: Int,
            p3: Int
          ) {
          }

        })
  }

  private fun calculateTotal() {
    amount = (itemPrice * itemQuantity).round2Decimal()
    tax = (amount * 0.09).round2Decimal()
    totalPrice = (amount + tax + tax).round2Decimal()
    Log.d(
        "total",
        "itemPrice = $itemPrice,  itemQuantity = $itemQuantity, amount = $amount, tax = $tax, totalPrice = $totalPrice"
    )

    tvIgst.text = tax.toString()
    tvSgst.text = tax.toString()
    tvTotal.text = totalPrice.toString()

  }

  private fun addItem() {
    if (itemName == null) {
      showToast(R.string.enter_item_name)
      return
    }

    if (itemQuantity == 0) {
      showToast(R.string.enter_item_quantity)
      return
    }

    if (itemPrice == 0.0) {
      showToast(R.string.enter_item_rate)
      return
    }

    listener.onItemAdded(
        Item(
            id = -1,
            itemName = itemName!!, quantity = itemQuantity, price = itemPrice, sgst = tax,
            igst = tax, totalPrice = totalPrice, purchaseId = -1
        )
    )
    Log.d("On CLICK", "dismiss")
    dismiss()
  }

  private fun showToast(msg: Int) {
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        .show()
  }

  override fun onAttach(context: Context?) {
    super.onAttach(context)
    if (context is OnClickListener) {
      listener = context
    }
  }

  override fun onStart() {
    super.onStart()
    val dialog = dialog
    if (dialog != null) {
      val width = ViewGroup.LayoutParams.MATCH_PARENT
      val height = ViewGroup.LayoutParams.WRAP_CONTENT
      dialog.window?.setLayout(width, height)
    }
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModelFactory = AddItemViewModelFactory(
        SaleDatabase.getInstance(activity!!).itemDao()
    )

    viewModel = ViewModelProviders.of(this, viewModelFactory)
        .get(AddItemViewModel::class.java)

  }

  interface OnClickListener {
    fun onItemAdded(item: Item)
  }

  companion object {
    fun newInstance() = AddItemFragment()
  }
}
