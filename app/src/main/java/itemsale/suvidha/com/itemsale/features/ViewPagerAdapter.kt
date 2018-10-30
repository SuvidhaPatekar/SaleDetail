package itemsale.suvidha.com.itemsale.features

import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fragmentManager: FragmentManager) : FragmentStatePagerAdapter(
    fragmentManager
) {

    private val fragments = mutableListOf<Fragment>()
    private val titles = mutableListOf<String>()
    private val actualFragmentsInPager: Array<Fragment?> = arrayOfNulls(20)

    fun addFragment(
        fragment: Fragment,
        title: String
    ) {
        fragments.add(fragment)
        titles.add(title)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return titles[position]
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