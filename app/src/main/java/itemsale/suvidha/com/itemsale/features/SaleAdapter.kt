package itemsale.suvidha.com.itemsale.features

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.features.SaleAdapter.SaleViewHolder
import itemsale.suvidha.com.itemsale.model.entity.Sale
import kotlinx.android.synthetic.main.item_sale.view.btnDelete
import kotlinx.android.synthetic.main.item_sale.view.btnPay
import kotlinx.android.synthetic.main.item_sale.view.tvBalanceAmount
import kotlinx.android.synthetic.main.item_sale.view.tvDate
import kotlinx.android.synthetic.main.item_sale.view.tvName
import kotlinx.android.synthetic.main.item_sale.view.tvTotalAmount

class SaleAdapter : RecyclerView.Adapter<SaleViewHolder>() {

  private var sales: List<Sale> = emptyList()
  private var paid: Int = 0
  private lateinit var listener: Listener

  fun setSales(
    sales: List<Sale>,
    paid: Int
  ) {
    this.sales = sales
    this.paid = paid
    notifyDataSetChanged()
  }

  fun setListener(listener: Listener) {
    this.listener = listener
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(
    viewGroup: ViewGroup,
    viewType: Int
  ): SaleViewHolder {
    return SaleViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_sale, viewGroup, false)
    )
  }

  override fun getItemCount(): Int {
    return sales.size
  }

  override fun onBindViewHolder(
    holder: SaleViewHolder,
    position: Int
  ) {
    holder.bindTo(sales[position])
  }

  inner class SaleViewHolder(
    itemView: View
  ) : RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.tvName
    private val tvDate: TextView = itemView.tvDate
    private val tvTotalAmount: TextView = itemView.tvTotalAmount
    private val tvBalanceAmount: TextView = itemView.tvBalanceAmount
    private val btnPay: Button = itemView.btnPay
    private val btnDelete: Button = itemView.btnDelete

    fun bindTo(sale: Sale) {
      tvName.text = sale.customerName
      tvDate.text = sale.date
      tvBalanceAmount.text = sale.balanceAmount.toString()
      tvTotalAmount.text = sale.totalAmount.toString()

      if (paid == 1) {
        btnPay.visibility = View.GONE
      } else {
        btnPay.visibility = View.VISIBLE
      }

      btnDelete.setOnClickListener {
        listener.onClickDelete(sale.id)
      }

      btnPay.setOnClickListener {
        listener.onClickPay(sale.id, sale.balanceAmount, sale.paidAmount, sale.totalAmount)
      }
    }
  }

  interface Listener {
    fun onClickDelete(id: Long)
    fun onClickPay(
      id: Long,
      balanceAmount: Double,
      paidAmount: Double,
      totalAmount: Double
    )
  }
}