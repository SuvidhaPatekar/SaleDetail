package itemsale.suvidha.com.itemsale.features.salesdetails

import android.app.AlertDialog
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.R.layout
import itemsale.suvidha.com.itemsale.features.SaleAdapter
import itemsale.suvidha.com.itemsale.features.SaleAdapter.Listener
import itemsale.suvidha.com.itemsale.features.salesdetails.SaleDetailViewModel.ViewState
import itemsale.suvidha.com.itemsale.model.SaleDatabase
import itemsale.suvidha.com.itemsale.round2Decimal
import kotlinx.android.synthetic.main.sale_detail_fragment.rvSaleDetail

class SaleDetailFragment : Fragment(), Listener {

  private lateinit var saleDetailAdapter: SaleAdapter
  private lateinit var viewModelFactory: SaleDetailViewModelFactory
  private lateinit var viewModel: SaleDetailViewModel
  private var isPaid: Int = 0

  companion object {
    private const val PAID = "PAID"

    fun newInstance(
      isPaid: Int
    ) = SaleDetailFragment().apply {
      arguments = Bundle(1).apply {
        putInt(PAID, isPaid)
      }
    }
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

    viewModel.viewState.observe(this, Observer { viewState ->
      handleViewState(viewState)
    })

    viewModel.getAll(isPaid)
  }

  private fun handleViewState(viewState: ViewState?) {
    viewState?.let {
      saleDetailAdapter.setSales(it.sales, isPaid)
    }
  }

  override fun onViewCreated(
    view: View,
    savedInstanceState: Bundle?
  ) {
    super.onViewCreated(view, savedInstanceState)
    saleDetailAdapter = SaleAdapter()
    saleDetailAdapter.setListener(this)
    rvSaleDetail.adapter = saleDetailAdapter

    arguments?.let {
      isPaid = it.getInt(PAID)
    }
  }

  override fun onClickDelete(id: Long) {
    viewModel.delete(id, paid = isPaid)
  }

  private fun showPayDialog(
    id: Long,
    balanceAmount: Double,
    paidAmount: Double,
    totalAmount: Double
  ) {
    val alert = AlertDialog.Builder(activity)

    val editText = EditText(context)
    editText.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL

    alert.setTitle("Pay Balance Amount")
    alert.setView(editText)

    alert.setPositiveButton("YES") { p0, p1 ->
      if (!editText.text.isNullOrEmpty()) {
        val amount = editText.text.toString()


        if (balanceAmount >= amount.toDouble()) {

          val totalPaidAmount = (paidAmount + amount.toDouble()).round2Decimal()
          var isPaid = 0

          if (totalAmount == totalPaidAmount) {
            isPaid = 1
          }

          viewModel.updatePayment(
              id, isPaid, (balanceAmount - amount.toDouble()).round2Decimal(), totalPaidAmount,
              isPaid
          )
        } else {
          showToast()
        }
      }
    }

    alert.setNegativeButton("NO") { p0, p1 -> }
    alert.show()
  }

  fun showToast() {
    Toast.makeText(activity, R.string.err_balance_amount, Toast.LENGTH_SHORT)
        .show()
  }

  override fun onClickPay(
    id: Long,
    balanceAmount: Double,
    paidAmount: Double,
    totalAmount: Double
  ) {
    showPayDialog(id, balanceAmount, paidAmount, totalAmount)
  }
}
