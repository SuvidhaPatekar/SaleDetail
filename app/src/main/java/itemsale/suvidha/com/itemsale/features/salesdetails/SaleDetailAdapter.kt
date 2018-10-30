package itemsale.suvidha.com.itemsale.features.salesdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import itemsale.suvidha.com.itemsale.R
import itemsale.suvidha.com.itemsale.model.entity.Item
import kotlinx.android.synthetic.main.item_sale_detail.view.tvId
import kotlinx.android.synthetic.main.item_sale_detail.view.tvItemName
import kotlinx.android.synthetic.main.item_sale_detail.view.tvQuantity
import kotlinx.android.synthetic.main.item_sale_detail.view.tvRate
import kotlinx.android.synthetic.main.item_sale_detail.view.tvTotalAmount

class SaleDetailAdapter : RecyclerView.Adapter<ViewHolder>() {

  private var items: List<Item> = emptyList()
  private val TYPE_HEADER = 1
  private val TYPE_ITEM = 2

  fun setItems(items: List<Item>) {
    this.items = items
    notifyDataSetChanged()
  }

  override fun getItemViewType(position: Int): Int {
    return if (position == 0) TYPE_HEADER
    else TYPE_ITEM
  }

  override fun onCreateViewHolder(
    viewGroup: ViewGroup,
    viewType: Int
  ): ViewHolder {
    return if (viewType == TYPE_HEADER) {
      SaleHeaderViewHolder(
          LayoutInflater.from(viewGroup.context)
              .inflate(
                  R.layout.item_sale_header, viewGroup, false
              )
      )
    } else {
      SaleDetailViewHolder(
          LayoutInflater.from(viewGroup.context)
              .inflate(R.layout.item_sale_detail, viewGroup, false)
      )
    }
  }

  override fun getItemCount(): Int {
    return if (items.size > 0) items.size + 1 else 0
  }

  override fun onBindViewHolder(
    holder: ViewHolder,
    position: Int
  ) {
    (holder as? SaleDetailViewHolder)?.bindTo(items[position - 1], position - 1)
  }

  inner class SaleHeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

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