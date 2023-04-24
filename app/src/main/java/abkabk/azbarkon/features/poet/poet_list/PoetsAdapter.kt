package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.databinding.ItemPoetBinding
import abkabk.azbarkon.features.poet.model.PoetUi
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PoetsAdapter(
    private val clickListener: (PoetUi) -> Unit,
) : ListAdapter<PoetUi, PoetViewHolder>(PoetUiDiffUtil) , Filterable{

    init {
        setHasStableIds(true)
    }

    var tracker: SelectionTracker<Long>? = null

    private var list = listOf<PoetUi>()

    fun setData(list: List<PoetUi>){
        this.list = list
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoetViewHolder {
        return PoetViewHolder(
            ItemPoetBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: PoetViewHolder, position: Int) {
        tracker?.let {
            holder.bindData(currentList[position], it.isSelected(currentList[position].id!!.toLong()))
        }
    }

    override fun getItemId(position: Int) = currentList[position].id!!.toLong()

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<PoetUi>()
            if (constraint == null || constraint.isEmpty()) {
                filteredList.addAll(list)
            } else {
                for (item in list) {
                    if (item.name?.contains(constraint) == true) {
                        filteredList.add(item)
                    }
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence?, filterResults: FilterResults?) {
            submitList(filterResults?.values as MutableList<PoetUi>)
        }

    }

}




class PoetViewHolder(
    private val viewBinding: ItemPoetBinding,
    private val itemClickListener: (PoetUi) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root){

    init {
        with(viewBinding) {
        }
    }

    fun bindData(poetUi: PoetUi, isActivated: Boolean = false) {
        with(viewBinding) {

            itemView.isActivated = isActivated

            root.setOnClickListener {
                itemClickListener(poetUi)
            }

            poetName.text = poetUi.name

            Glide
                .with(itemView.context)
                .load(poetUi.loadableImageUrl)
                .into(poetImg)

            if (poetUi.isPinned){
                pinImg.visibility = View.VISIBLE
            }else{
                pinImg.visibility = View.GONE
            }
        }
    }

    fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
        object : ItemDetailsLookup.ItemDetails<Long>() {
            override fun getPosition(): Int = adapterPosition
            override fun getSelectionKey(): Long = itemId
        }
}


object PoetUiDiffUtil : DiffUtil.ItemCallback<PoetUi>() {
    override fun areItemsTheSame(oldItem: PoetUi, newItem: PoetUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PoetUi, newItem: PoetUi): Boolean =
        oldItem == newItem
}
