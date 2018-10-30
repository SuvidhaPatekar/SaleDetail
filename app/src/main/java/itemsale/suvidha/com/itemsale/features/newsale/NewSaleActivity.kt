package itemsale.suvidha.com.itemsale.features.newsale

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.jakewharton.rxbinding.widget.RxTextView
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.convertToString
import itemsale.suvidha.com.itemsale.features.newsale.AddItemFragment.AddItemDoneClickListener
import itemsale.suvidha.com.itemsale.features.newsale.NewSaleViewModel.ErrorViewState
import itemsale.suvidha.com.itemsale.features.salesdetails.SaleDetailAdapter
import itemsale.suvidha.com.itemsale.getCurrentDateTime
import itemsale.suvidha.com.itemsale.model.SaleDatabase
import itemsale.suvidha.com.itemsale.model.entity.Item
import kotlinx.android.synthetic.main.activity_new_sale.toolbar
import kotlinx.android.synthetic.main.content_new_sale.btnAdd
import kotlinx.android.synthetic.main.content_new_sale.btnDone
import kotlinx.android.synthetic.main.content_new_sale.checkbox
import kotlinx.android.synthetic.main.content_new_sale.etAmount
import kotlinx.android.synthetic.main.content_new_sale.etCustomerName
import kotlinx.android.synthetic.main.content_new_sale.rvItems
import kotlinx.android.synthetic.main.content_new_sale.tvDate
import kotlinx.android.synthetic.main.item_subtotal.tvSubtotalAmount
import kotlinx.android.synthetic.main.item_subtotal.tvTotalQuantity

class NewSaleActivity : AppCompatActivity(), AddItemDoneClickListener {

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

    newSaleViewModel.errorViewState.observe(this, Observer { viewState ->
      handleErrorViewState(viewState)
    })
    btnAdd.setOnClickListener {
      val newFragment =
        AddItemFragment.newInstance()
      newFragment.show(supportFragmentManager, "ITEM DETAILS")
    }

    items = ArrayList()
    saleDetailAdapter = SaleDetailAdapter()
    rvItems.adapter = saleDetailAdapter

    RxTextView.textChanges(etAmount)
        .subscribe { text ->
          if (!text.isNullOrEmpty()) {
            newSaleViewModel.checkPaidAmount(text.toString().toDouble())
          } else {
            newSaleViewModel.checkPaidAmount(0.0)
          }
        }

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

  private fun handleErrorViewState(errorViewState: ErrorViewState?) {
    errorViewState?.let {
      if (it.error > 0) {
        showToast(it.error)
      }
    }
  }

  private fun handleViewState(viewState: NewSaleViewModel.ViewState?) {
    viewState?.let {

      if (it.isDone) {
        finish()
        return
      }

      saleDetailAdapter.setItems(it.items)
      tvSubtotalAmount.text = it.subTotal.toString()
      tvTotalQuantity.text = it.totalQuantity.toString()
      checkbox.isChecked = it.isPaid
    }
  }

  private fun showToast(msg: Int) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT)
        .show()
  }

  override fun onDoneClick(item: Item) {
    newSaleViewModel.addItem(item)
  }
}
