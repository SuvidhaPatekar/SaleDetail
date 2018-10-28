package itemsale.suvidha.com.itemsale.features

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.features.SaleDetailAdapter.SaleDetailViewHolder
import itemsale.suvidha.com.itemsale.model.entity.Item
import kotlinx.android.synthetic.main.item_sale_detail.view.tvId
import kotlinx.android.synthetic.main.item_sale_detail.view.tvItemName
import kotlinx.android.synthetic.main.item_sale_detail.view.tvQuantity
import kotlinx.android.synthetic.main.item_sale_detail.view.tvRate
import kotlinx.android.synthetic.main.item_sale_detail.view.tvTotalAmount

class SaleDetailAdapter : RecyclerView.Adapter<SaleDetailViewHolder>() {

  private var items: ArrayList<Item>? = null

  fun setItems(items: ArrayList<Item>) {
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
    if (items != null) {
      return items?.size!!
    }
    return 0
  }

  override fun onBindViewHolder(
    holder: SaleDetailViewHolder,
    position: Int
  ) {
    holder.bindTo(item = items?.get(position), position = position)
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
      item: Item?,
      position: Int
    ) {
      tvId.text = (position + 1).toString()
      tvItemName.text = item?.itemName
      tvQuantity.text = item?.quantity.toString()
      tvRate.text = item?.price.toString()
      tvTotalAmount.text = item?.totalPrice.toString()
    }
  }
}