package itemsale.suvidha.com.itemsale.features.newsale

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.convertToString
import itemsale.suvidha.com.itemsale.features.newsale.AddItemFragment.OnClickListener
import itemsale.suvidha.com.itemsale.features.salesdetails.SaleDetailAdapter
import itemsale.suvidha.com.itemsale.getCurrentDateTime
import itemsale.suvidha.com.itemsale.model.SaleDatabase
import itemsale.suvidha.com.itemsale.model.entity.Item
import kotlinx.android.synthetic.main.activity_new_sale.fab
import kotlinx.android.synthetic.main.activity_new_sale.toolbar
import kotlinx.android.synthetic.main.content_new_sale.btnDone
import kotlinx.android.synthetic.main.content_new_sale.checkbox
import kotlinx.android.synthetic.main.content_new_sale.etAmount
import kotlinx.android.synthetic.main.content_new_sale.etCustomerName
import kotlinx.android.synthetic.main.content_new_sale.rvItems
import kotlinx.android.synthetic.main.content_new_sale.tvDate
import kotlinx.android.synthetic.main.item_subtotal.tvSubtotalAmount
import kotlinx.android.synthetic.main.item_subtotal.tvTotalQuantity

class NewSaleActivity : AppCompatActivity(), OnClickListener {

  private lateinit var saleDetailAdapter: SaleDetailAdapter
  private lateinit var items: ArrayList<Item>
  private lateinit var newSaleViewModel: NewSaleViewModel
  private lateinit var viewModelFactory: NewSaleViewModelFactory

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_sale)
    setSupportActionBar(toolbar)

    viewModelFactory = NewSaleViewModelFactory(
        SaleDatabase.getInstance(applicationContext!!).saleDao(),
        SaleDatabase.getInstance(applicationContext!!).itemDao()
    )

    newSaleViewModel = ViewModelProviders.of(this, viewModelFactory)
        .get(NewSaleViewModel::class.java)

    newSaleViewModel.viewState.observe(this, Observer { viewState ->
      handleViewState(viewState)
    })

    fab.setOnClickListener {
      val newFragment =
        AddItemFragment.newInstance()
      newFragment.show(supportFragmentManager, "ITEM DETAILS")
    }

    items = ArrayList()
    saleDetailAdapter = SaleDetailAdapter()
    rvItems.isNestedScrollingEnabled = false
    rvItems.adapter = saleDetailAdapter

    etAmount.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(text: Editable?) {
        if (!text.isNullOrEmpty()) {
          newSaleViewModel.checkPaidAmount(text.toString().toDouble())
        } else {
          newSaleViewModel.checkPaidAmount(0.0)
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

    val date = getCurrentDateTime()
    val dateString = date.convertToString()
    tvDate.text = dateString
    btnDone.setOnClickListener {
      newSaleViewModel.addSale(
          etCustomerName.text.toString(), dateString,
          etAmount.text.toString()
      )
    }
  }

  private fun handleViewState(viewState: NewSaleViewModel.ViewState?) {
    viewState?.let {
      saleDetailAdapter.setItems(it.items)
      tvSubtotalAmount.text = it.subTotal.toString()
      tvTotalQuantity.text = it.totalQuantity.toString()
      checkbox.isChecked = it.isPaid

      if (it.isGreaterAmount) {
        showToast(R.string.err_greater_amount)
      }

      if (it.isNullCustomerName) {
        showToast(R.string.err_name)
      }

      if (it.isNullAmount) {
        showToast(R.string.err_amount)
      }

      if (it.isNullDate) {
        showToast(R.string.err_date)
      }

      if (it.emptyItems) {
        showToast(R.string.err_items)
      }

      if (it.isDone) {
        finish()
      }
    }
  }

  private fun showToast(msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        .show()
  }

  override fun onItemAdded(item: Item) {
    newSaleViewModel.addItem(item)
  }
}
