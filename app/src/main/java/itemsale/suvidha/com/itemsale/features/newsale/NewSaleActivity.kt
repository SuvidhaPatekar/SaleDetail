package itemsale.suvidha.com.itemsale.features.newsale

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.features.newsale.AddItemFragment.OnClickListener
import itemsale.suvidha.com.itemsale.features.salesdetails.SaleDetailAdapter
import itemsale.suvidha.com.itemsale.model.entity.Item
import itemsale.suvidha.com.itemsale.round2Decimal
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
  private var subTotal = 0.0
  private var quantity = 0
  private var isPaid: Boolean = false

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_sale)
    setSupportActionBar(toolbar)

    fab.setOnClickListener {
      val newFragment =
        AddItemFragment.newInstance()
      newFragment.show(supportFragmentManager, "ITEM DETAILS")
    }
    items = ArrayList()
    saleDetailAdapter = SaleDetailAdapter()
    val dividerItemDecoration = DividerItemDecoration(
        rvItems.context,
        RecyclerView.VERTICAL
    )
    rvItems.addItemDecoration(dividerItemDecoration)
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
    tvSubtotalAmount.text = subTotal.toString()
    tvTotalQuantity.text = quantity.toString()
    checkbox.isChecked = isPaid
  }

  override fun onItemAdded(item: Item) {
    items.add(0, item)
    saleDetailAdapter.setItems(items)
    quantity += item.quantity
    subTotal = (subTotal + item.totalPrice).round2Decimal()
    setData()
  }
}
