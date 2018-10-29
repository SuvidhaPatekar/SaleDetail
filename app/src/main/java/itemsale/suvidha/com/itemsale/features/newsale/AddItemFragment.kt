package itemsale.suvidha.com.itemsale.features.newsale

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.R.layout
import itemsale.suvidha.com.itemsale.features.newsale.AddItemViewModel.ViewState
import itemsale.suvidha.com.itemsale.model.SaleDatabase
import itemsale.suvidha.com.itemsale.model.entity.Item
import kotlinx.android.synthetic.main.fragment_item_detail.btnDone
import kotlinx.android.synthetic.main.fragment_item_detail.etItemName
import kotlinx.android.synthetic.main.fragment_item_detail.etItemPrice
import kotlinx.android.synthetic.main.fragment_item_detail.etItemQuantity
import kotlinx.android.synthetic.main.fragment_item_detail.tvIgst
import kotlinx.android.synthetic.main.fragment_item_detail.tvSgst
import kotlinx.android.synthetic.main.fragment_item_detail.tvTotal

class AddItemFragment : DialogFragment() {
  private lateinit var listener: OnClickListener
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
      viewModel.addItem(itemName = itemName)
    }
    etItemQuantity.addTextChangedListener(
        object : TextWatcher {
          override fun afterTextChanged(quantity: Editable?) {

            if (quantity.isNullOrEmpty()) {
              viewModel.addQuantity(0)
            } else {
              viewModel.addQuantity(
                  quantity.toString()
                      .toInt()
              )
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

    etItemPrice.addTextChangedListener(
        object : TextWatcher {
          override fun afterTextChanged(price: Editable?) {
            if (price.isNullOrEmpty()) {
              viewModel.addPrice(0.0)
            } else {
              viewModel.addPrice(
                  price.toString().toDouble()
              )
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

    viewModel.viewState.observe(this, Observer { viewState ->
      handleViewState(viewState)
    })
  }

  private fun handleViewState(viewState: ViewState?) {
    viewState?.let {
      tvIgst.text = it.tax.toString()
      tvSgst.text = it.tax.toString()
      tvTotal.text = it.totalPrice.toString()

      if (it.isNullName) {
        showToast(R.string.enter_item_name)
      }

      if (it.isQuantityAdded) {
        showToast(R.string.enter_item_quantity)
      }

      if (it.isPriceAdded) {
        showToast(R.string.enter_item_rate)
      }

      if (it.itemAdded) {
        listener.onItemAdded(
            Item(
                id = -1,
                itemName = itemName!!, quantity = it.itemQuantity, price = it.itemPrice,
                sgst = it.tax,
                igst = it.tax, totalPrice = it.totalPrice, purchaseId = -1
            )
        )
        dismiss()
      }
    }
  }

  companion object {
    fun newInstance() = AddItemFragment()
  }

  private fun showToast(msg: Int) {
    Toast.makeText(activity, msg, Toast.LENGTH_SHORT)
        .show()
  }

  interface OnClickListener {
    fun onItemAdded(item: Item)
  }
}
