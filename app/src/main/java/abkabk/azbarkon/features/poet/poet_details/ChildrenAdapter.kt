package abkabk.azbarkon.features.poet.poet_details

import abkabk.azbarkon.databinding.ItemChildrenBinding
import abkabk.azbarkon.features.poet.domain.Children
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class ChildrenAdapter(
    private val clickListener: (Children) -> Unit
) : ListAdapter<Children, ChildrenViewHolder>(ChildrenDiffUtil){

    private var list = listOf<Children>()

    fun setData(list: List<Children>){
        this.list = list
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildrenViewHolder {
        return ChildrenViewHolder(
            ItemChildrenBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: ChildrenViewHolder, position: Int) {
        holder.bindData(currentList[position])
    }

}




class ChildrenViewHolder(
    private val viewBinding: ItemChildrenBinding,
    private val itemClickListener: (Children) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root){

    init {
        with(viewBinding) {
        }
    }

    fun bindData(children: Children) {
        with(viewBinding) {
            root.setOnClickListener {
                itemClickListener(children)
            }
            childrenName.text = children.title
        }
    }
}


object ChildrenDiffUtil : DiffUtil.ItemCallback<Children>() {
    override fun areItemsTheSame(oldItem: Children, newItem: Children): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Children, newItem: Children): Boolean =
        oldItem == newItem
}
