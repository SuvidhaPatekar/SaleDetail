package itemsale.suvidha.com.itemsale.features.salesdetails

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.features.ViewPagerAdapter
import itemsale.suvidha.com.itemsale.features.newsale.NewSaleActivity
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.activity_main.tabLayout
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.viewPager

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: ViewPagerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    setSupportActionBar(toolbar)

    adapter = ViewPagerAdapter(supportFragmentManager)
    viewPager.adapter = adapter
    tabLayout.setupWithViewPager(viewPager)

    adapter.addFragment(SaleDetailFragment.newInstance(0))
    adapter.addFragment(SaleDetailFragment.newInstance(1))
    adapter.notifyDataSetChanged()

    fab.setOnClickListener {
      val intent = Intent(this, NewSaleActivity::class.java)
      startActivity(intent)
    }
  }

}
