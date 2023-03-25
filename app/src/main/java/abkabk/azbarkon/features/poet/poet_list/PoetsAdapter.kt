package abkabk.azbarkon.features.poet.poet_list

import abkabk.azbarkon.databinding.ItemPoetBinding
import abkabk.azbarkon.features.poet.domain.model.Poet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PoetsAdapter(
    private val clickListener: (Poet) -> Unit
) : ListAdapter<Poet, PoetViewHolder>(PoetDiffUtil) , Filterable{

    private var list = listOf<Poet>()

    fun setData(list: List<Poet>){
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
        holder.bindData(currentList[position])
    }

    override fun getFilter(): Filter {
        return customFilter
    }

    private val customFilter = object : Filter() {
        override fun performFiltering(constraint: CharSequence?): FilterResults {
            val filteredList = mutableListOf<Poet>()
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
            submitList(filterResults?.values as MutableList<Poet>)
        }

    }

}




class PoetViewHolder(
    private val viewBinding: ItemPoetBinding,
    private val itemClickListener: (Poet) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root){

    init {
        with(viewBinding) {
        }
    }

    fun bindData(poet: Poet) {
        with(viewBinding) {
            root.setOnClickListener {
                itemClickListener(poet)
            }
            poetName.text = poet.name

            Glide
                .with(itemView.context)
                .load(poet.loadableImageUrl)
                .into(poetImg)
        }
    }
}


object PoetDiffUtil : DiffUtil.ItemCallback<Poet>() {
    override fun areItemsTheSame(oldItem: Poet, newItem: Poet): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Poet, newItem: Poet): Boolean =
        oldItem == newItem
}
