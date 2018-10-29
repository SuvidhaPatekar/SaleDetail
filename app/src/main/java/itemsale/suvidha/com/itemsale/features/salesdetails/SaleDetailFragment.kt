package itemsale.suvidha.com.itemsale.features.salesdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import itemsale.suvidha.com.itemsale.R.layout
import itemsale.suvidha.com.itemsale.features.SaleAdapter
import itemsale.suvidha.com.itemsale.model.SaleDatabase
import kotlinx.android.synthetic.main.sale_detail_fragment.rvSaleDetail

class SaleDetailFragment : Fragment() {

  private lateinit var saleDetailAdapter: SaleAdapter
  private lateinit var viewModelFactory: SaleDetailViewModelFactory
  private lateinit var viewModel: SaleDetailViewModel

  companion object {
    fun newInstance() = SaleDetailFragment()
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    return inflater.inflate(layout.sale_detail_fragment, container, false)
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)

    viewModelFactory = SaleDetailViewModelFactory(
        SaleDatabase.getInstance(activity!!).saleDao()
    )

    viewModel = ViewModelProviders.of(this, viewModelFactory)
        .get(SaleDetailViewModel::class.java)
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    saleDetailAdapter = SaleAdapter()
    rvSaleDetail.adapter = saleDetailAdapter
  }
}
