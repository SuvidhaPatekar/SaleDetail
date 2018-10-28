package itemsale.suvidha.com.itemsale

import android.app.Dialog
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_item_detail.btnDone
import kotlinx.android.synthetic.main.fragment_item_detail.etItemPrice
import kotlinx.android.synthetic.main.fragment_item_detail.etItemQuantity
import kotlinx.android.synthetic.main.fragment_item_detail.tvIgst
import kotlinx.android.synthetic.main.fragment_item_detail.tvSgst
import kotlinx.android.synthetic.main.fragment_item_detail.tvTotal

class ItemDetailFragment : DialogFragment() {
  private var itemQuantity = 0
  private var itemPrice = 0.0
  private var totalPrice = 0.0
  private var tax = 0.0
  private var amount = 0.0

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.fragment_item_detail, container, false)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    btnDone.setOnClickListener {

    }

    calculateTotal()
    etItemQuantity.addTextChangedListener(object : TextWatcher {
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

    etItemPrice.addTextChangedListener(object : TextWatcher {
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
  }

  private fun calculateTotal() {
    amount = round2Decimal(itemPrice * itemQuantity)
    tax = round2Decimal(amount * 0.09)
    totalPrice = round2Decimal(amount + tax + tax)
    Log.d(
        "total",
        "itemPrice = $itemPrice,  itemQuantity = $itemQuantity, amount = $amount, tax = $tax, totalPrice = $totalPrice"
    )

    tvIgst.text = tax.toString()
    tvSgst.text = tax.toString()
    tvTotal.text = totalPrice.toString()
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

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    val dialog = super.onCreateDialog(savedInstanceState)
    dialog.setTitle("ITEM DETAILS")
    return dialog
  }

  interface OnClickListener {
    fun onItemAdded()
  }

  companion object {
    fun newInstance() = ItemDetailFragment()
  }
}
