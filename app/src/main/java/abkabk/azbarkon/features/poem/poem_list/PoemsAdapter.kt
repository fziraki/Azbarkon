package abkabk.azbarkon.features.poem.poem_list

import abkabk.azbarkon.databinding.ItemPoemBinding
import abkabk.azbarkon.features.poet.domain.Poem
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class PoemsAdapter(
    private val clickListener: (Poem) -> Unit
) : ListAdapter<Poem, PoemViewHolder>(PoemDiffUtil){

    private var list = listOf<Poem>()

    fun setData(list: List<Poem>){
        this.list = list
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoemViewHolder {
        return PoemViewHolder(
            ItemPoemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: PoemViewHolder, position: Int) {
        holder.bindData(currentList[position])
    }

}




class PoemViewHolder(
    private val viewBinding: ItemPoemBinding,
    private val itemClickListener: (Poem) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root){

    init {
        with(viewBinding) {
        }
    }

    fun bindData(poem: Poem) {
        with(viewBinding) {
            root.setOnClickListener {
                itemClickListener(poem)
            }
            poemName.text = String.format("%s - %s", poem.title, poem.excerpt)
        }
    }
}


object PoemDiffUtil : DiffUtil.ItemCallback<Poem>() {
    override fun areItemsTheSame(oldItem: Poem, newItem: Poem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Poem, newItem: Poem): Boolean =
        oldItem == newItem
}
