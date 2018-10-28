package itemsale.suvidha.com.itemsale.features

import android.arch.persistence.room.Room
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.R.layout
import itemsale.suvidha.com.itemsale.R.string
import itemsale.suvidha.com.itemsale.model.ItemDatabase
import kotlinx.android.synthetic.main.activity_main.fab
import kotlinx.android.synthetic.main.activity_main.tabLayout
import kotlinx.android.synthetic.main.activity_main.toolbar
import kotlinx.android.synthetic.main.activity_main.viewPager

class MainActivity : AppCompatActivity() {

  private lateinit var adapter: ViewPagerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(layout.activity_main)
    setSupportActionBar(toolbar)

    adapter = ViewPagerAdapter(supportFragmentManager)
    viewPager.adapter = adapter
    tabLayout.setupWithViewPager(viewPager)

    adapter.addFragment(SaleDetailFragment.newInstance())
    adapter.addFragment(SaleDetailFragment.newInstance())
    adapter.notifyDataSetChanged()

    fab.setOnClickListener { view ->
      val intent = Intent(this, NewSaleActivity::class.java)
      startActivity(intent)
    }

    initDatabase()
  }

  private fun initDatabase() {
    val DATABASE_NAME = getString(R.string.item_db)
    Room.databaseBuilder(
        applicationContext,
        ItemDatabase::class.java, DATABASE_NAME
    )
        .fallbackToDestructiveMigration()
        .build()
  }
}
