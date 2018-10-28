package itemsale.suvidha.com.itemsale.features

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.view.ViewGroup

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(
    fragmentManager
) {

  private val fragments = mutableListOf<Fragment>()
  private val actualFragmentsInPager: Array<Fragment?> = arrayOfNulls(20)

  fun addFragment(
    fragment: Fragment
  ) {
    fragments.add(fragment)
  }

  override fun getPageTitle(position: Int): CharSequence? {
    if (position == 0) {
      return "PARTIALLY PAID"
    } else if (position == 1) {
      return "PAID"
    }
    return "PAID"
  }

  fun getFragment(position: Int): Fragment? {
    return actualFragmentsInPager[position]
  }

  override fun instantiateItem(
    container: ViewGroup,
    position: Int
  ): Any {
    val createdFragment = super.instantiateItem(container, position) as Fragment
    actualFragmentsInPager[position] = createdFragment
    return createdFragment
  }

  override fun getItem(position: Int) = fragments[position]

  override fun getCount() = fragments.size
}