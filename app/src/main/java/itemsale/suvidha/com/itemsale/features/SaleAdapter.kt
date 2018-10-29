package itemsale.suvidha.com.itemsale.features

import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.R.layout
import itemsale.suvidha.com.itemsale.features.SaleAdapter.SaleViewHolder
import kotlinx.android.synthetic.main.item_sale.view.btnDelete
import kotlinx.android.synthetic.main.item_sale.view.btnPay
import kotlinx.android.synthetic.main.item_sale.view.tvBalanceAmount
import kotlinx.android.synthetic.main.item_sale.view.tvDate
import kotlinx.android.synthetic.main.item_sale.view.tvName
import kotlinx.android.synthetic.main.item_sale.view.tvTotalAmount

class SaleAdapter : RecyclerView.Adapter<SaleViewHolder>() {
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
    return 3
  }

  override fun onBindViewHolder(
    holder: SaleViewHolder,
    position: Int
  ) {
    holder.bindTo()
  }

  inner class SaleViewHolder(
    itemView: View
  ) : RecyclerView.ViewHolder(itemView) {
    private val tvName: TextView = itemView.tvName
    private val tvDate: TextView = itemView.tvDate
    private val tvTotalAmount: TextView = itemView.tvTotalAmount
    private val tvBalanceAmount: TextView = itemView.tvBalanceAmount

    init {
      itemView.btnDelete.setOnClickListener {
        Log.d("btn delete", "on click")
      }

      itemView.btnPay.setOnClickListener {
        Log.d("btn pay", "on click")
      }
    }

    fun bindTo() {
      tvName.text = "Suvidha"
      tvDate.text = "17/12/1789"
      tvBalanceAmount.text = "1000"
      tvTotalAmount.text = "1000"
    }
  }
}