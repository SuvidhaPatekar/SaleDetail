package itemsale.suvidha.com.itemsale.features.salesdetails

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.features.salesdetails.SaleDetailAdapter.SaleDetailViewHolder
import itemsale.suvidha.com.itemsale.model.entity.Item
import kotlinx.android.synthetic.main.item_sale_detail.view.tvId
import kotlinx.android.synthetic.main.item_sale_detail.view.tvItemName
import kotlinx.android.synthetic.main.item_sale_detail.view.tvQuantity
import kotlinx.android.synthetic.main.item_sale_detail.view.tvRate
import kotlinx.android.synthetic.main.item_sale_detail.view.tvTotalAmount

class SaleDetailAdapter : RecyclerView.Adapter<SaleDetailViewHolder>() {

  private var items: List<Item> = emptyList()

  fun setItems(items: List<Item>) {
    this.items = items
    notifyDataSetChanged()
  }

  override fun onCreateViewHolder(
    viewGroup: ViewGroup,
    p1: Int
  ): SaleDetailViewHolder {
    return SaleDetailViewHolder(
        LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_sale_detail, viewGroup, false)
    )
  }

  override fun getItemCount(): Int {
    return items.size
  }

  override fun onBindViewHolder(
    holder: SaleDetailViewHolder,
    position: Int
  ) {
    holder.bindTo(item = items[position], position = position)
  }

  inner class SaleDetailViewHolder(
    itemView: View
  ) : RecyclerView.ViewHolder(itemView) {
    private var tvId = itemView.tvId
    private var tvItemName = itemView.tvItemName
    private var tvQuantity = itemView.tvQuantity
    private var tvRate = itemView.tvRate
    private var tvTotalAmount = itemView.tvTotalAmount
    fun bindTo(
      item: Item,
      position: Int
    ) {
      tvId.text = (position + 1).toString()
      tvItemName.text = item.itemName
      tvQuantity.text = item.quantity.toString()
      tvRate.text = item.price.toString()
      tvTotalAmount.text = item.totalPrice.toString()
    }
  }
}