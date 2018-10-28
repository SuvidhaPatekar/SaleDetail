package itemsale.suvidha.com.itemsale.features

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.features.ItemDetailFragment.OnClickListener
import itemsale.suvidha.com.itemsale.model.entity.Item
import kotlinx.android.synthetic.main.activity_new_sale.fab
import kotlinx.android.synthetic.main.activity_new_sale.toolbar
import kotlinx.android.synthetic.main.content_new_sale.rvItems

class NewSaleActivity : AppCompatActivity(), OnClickListener {

  private lateinit var saleDetailAdapter: SaleDetailAdapter
  private lateinit var items: ArrayList<Item>

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_sale)
    setSupportActionBar(toolbar)

    fab.setOnClickListener {
      val newFragment = ItemDetailFragment.newInstance()
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
  }

  override fun onItemAdded(item: Item) {
    items.add(item)
    saleDetailAdapter.setItems(items)
  }
}
