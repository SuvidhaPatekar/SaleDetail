package itemsale.suvidha.com.itemsale

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_sale.fab
import kotlinx.android.synthetic.main.activity_new_sale.toolbar

class NewSaleActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_new_sale)
    setSupportActionBar(toolbar)

    fab.setOnClickListener { view ->
      val newFragment = ItemDetailFragment.newInstance()
      newFragment.show(supportFragmentManager, "ITEM DETAILS")
    }
  }
}
