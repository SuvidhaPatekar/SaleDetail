package itemsale.suvidha.com.itemsale.features

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import itemsale.suvidha.com.itemsale.R.layout
import itemsale.suvidha.com.itemsale.features.ItemDetailFragment.OnClickListener
import kotlinx.android.synthetic.main.activity_new_sale.fab
import kotlinx.android.synthetic.main.activity_new_sale.toolbar

class NewSaleActivity : AppCompatActivity(), OnClickListener {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_new_sale)
    setSupportActionBar(toolbar)

    fab.setOnClickListener {
      val newFragment = ItemDetailFragment.newInstance()
      newFragment.show(supportFragmentManager, "ITEM DETAILS")
    }
  }

  override fun onItemAdded() {
  }
}
