package itemsale.suvidha.com.itemsale

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.RecyclerView.VERTICAL
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.sale_detail_fragment.rvSaleDetail

class SaleDetailFragment : Fragment() {

  private lateinit var saleDetailAdapter: SaleDetailAdapter

  companion object {
    fun newInstance() = SaleDetailFragment()
  }

  private lateinit var viewModel: SaleDetailViewModel

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(R.layout.sale_detail_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    viewModel = ViewModelProviders.of(this)
        .get(SaleDetailViewModel::class.java)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    saleDetailAdapter = SaleDetailAdapter()
    val dividerItemDecoration = DividerItemDecoration(
        rvSaleDetail.context,
        VERTICAL
    )
    rvSaleDetail.addItemDecoration(dividerItemDecoration)
    rvSaleDetail.adapter = saleDetailAdapter
  }
}
