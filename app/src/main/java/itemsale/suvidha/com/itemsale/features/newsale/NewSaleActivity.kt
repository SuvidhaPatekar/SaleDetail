package itemsale.suvidha.com.itemsale.features.newsale

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.features.newsale.AddItemFragment.OnClickListener
import itemsale.suvidha.com.itemsale.features.salesdetails.SaleDetailAdapter
import itemsale.suvidha.com.itemsale.model.entity.Item
import kotlinx.android.synthetic.main.activity_new_sale.fab
import kotlinx.android.synthetic.main.activity_new_sale.toolbar
import kotlinx.android.synthetic.main.content_new_sale.btnDone
import kotlinx.android.synthetic.main.content_new_sale.checkbox
import kotlinx.android.synthetic.main.content_new_sale.etAmount
import kotlinx.android.synthetic.main.content_new_sale.rvItems
import kotlinx.android.synthetic.main.content_new_sale.tvSubtotalAmount
import kotlinx.android.synthetic.main.content_new_sale.tvTotalQuantity

class NewSaleActivity : AppCompatActivity(), OnClickListener {

  private lateinit var saleDetailAdapter: SaleDetailAdapter
  private lateinit var items: ArrayList<Item>
  private lateinit var newSaleViewModel: NewSaleViewModel
  private var subTotal = 0.0
  private var isPaid: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_sale)
    setSupportActionBar(toolbar)

    newSaleViewModel = ViewModelProviders.of(this)
        .get(NewSaleViewModel::class.java)

    newSaleViewModel.viewState.observe(this, Observer { viewState ->
      viewState?.let {
        saleDetailAdapter.setItems(it.items)
        tvSubtotalAmount.text = it.subTotal.toString()
        tvTotalQuantity.text = it.totalQuantity.toString()
        checkbox.isChecked = it.isPaid
      }
    })
    fab.setOnClickListener {
      val newFragment =
        AddItemFragment.newInstance()
      newFragment.show(supportFragmentManager, "ITEM DETAILS")
    }
    items = ArrayList()
    saleDetailAdapter = SaleDetailAdapter()
    rvItems.adapter = saleDetailAdapter
    setData()

    etAmount.addTextChangedListener(object : TextWatcher {
      override fun afterTextChanged(text: Editable?) {
        isPaid = if (!text.isNullOrEmpty()) {
          text.toString().toDouble() == subTotal
        } else {
          false
        }
        setData()
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

    btnDone.setOnClickListener {

    }
  }

  private fun setData() {

  }

  override fun onItemAdded(item: Item) {
    newSaleViewModel.addItem(item)
    setData()
  }
}
