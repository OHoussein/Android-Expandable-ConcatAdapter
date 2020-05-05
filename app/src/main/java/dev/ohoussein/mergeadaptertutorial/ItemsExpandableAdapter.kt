package dev.ohoussein.mergeadaptertutorial

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import dev.ohoussein.mergeadaptertutorial.data.ItemsGroup
import kotlinx.android.synthetic.main.item_content.view.*
import kotlinx.android.synthetic.main.item_header.view.*
import kotlin.properties.Delegates
import kotlin.reflect.KProperty

class ItemsExpandableAdapter(private val itemsGroup: ItemsGroup) :
    RecyclerView.Adapter<ItemsExpandableAdapter.ViewHolder>() {

    companion object {
        private const val VIEW_TYPE_ITEM = 1
        private const val VIEW_TYPE_HEADER = 2

        private const val IC_EXPANDED_ROTATION_DEG = 0F
        private const val IC_COLLAPSED_ROTATION_DEG = 180F
    }

    var isExpanded: Boolean by Delegates.observable(true) { _: KProperty<*>, _: Boolean, newExpandedValue: Boolean ->
        if (newExpandedValue) {
            notifyItemRangeInserted(1, itemsGroup.items.size)
            //To update the header expand icon
            notifyItemChanged(0)
        } else {
            notifyItemRangeRemoved(1, itemsGroup.items.size)
            //To update the header expand icon
            notifyItemChanged(0)
        }
    }

    private val onHeaderClickListener = View.OnClickListener {
        isExpanded = !isExpanded
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun getItemCount(): Int = if (isExpanded) itemsGroup.items.size + 1 else 1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HEADER -> ViewHolder.HeaderVH(
                inflater.inflate(
                    R.layout.item_header,
                    parent,
                    false
                )
            )
            else -> ViewHolder.ItemVH(
                inflater.inflate(
                    R.layout.item_content,
                    parent,
                    false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when (holder) {
            is ViewHolder.ItemVH -> holder.bind(itemsGroup.items[position - 1])
            is ViewHolder.HeaderVH -> {
                holder.bind(itemsGroup.title, isExpanded, onHeaderClickListener)
            }
        }
    }

    private fun onExpanded() {
        notifyItemRangeInserted(1, itemsGroup.items.size)
        //To update the header expand icon
        notifyItemChanged(0)
    }

    private fun onCollapsed() {
        notifyItemRangeRemoved(1, itemsGroup.items.size)
        //To update the header expand icon
        notifyItemChanged(0)
    }


    sealed class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        class ItemVH(itemView: View) : ViewHolder(itemView) {

            fun bind(content: String) {
                itemView.apply {
                    itemView.tvItemContent.text = content
                }
            }
        }

        class HeaderVH(itemView: View) : ViewHolder(itemView) {
            internal val tvTitle = itemView.tvTitle
            internal val icExpand = itemView.icExpand

            fun bind(content: String, expanded: Boolean, onClickListener: View.OnClickListener) {
                tvTitle.text = content
                icExpand.rotation =
                    if (expanded) IC_EXPANDED_ROTATION_DEG else IC_COLLAPSED_ROTATION_DEG
                itemView.setOnClickListener(onClickListener)
            }
        }
    }
}


