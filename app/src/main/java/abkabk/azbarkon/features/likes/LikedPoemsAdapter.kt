package abkabk.azbarkon.features.likes

import abkabk.azbarkon.databinding.ItemPoemBinding
import abkabk.azbarkon.features.poem.model.PoemDetailsUi
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class LikedPoemsAdapter(
    private val clickListener: (PoemDetailsUi) -> Unit
) : ListAdapter<PoemDetailsUi, PoemDetailsUiViewHolder>(PoemDetailsUiDiffUtil){

    private var list = listOf<PoemDetailsUi>()

    fun setData(list: List<PoemDetailsUi>){
        this.list = list
        submitList(list)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PoemDetailsUiViewHolder {
        return PoemDetailsUiViewHolder(
            ItemPoemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            clickListener
        )
    }

    override fun onBindViewHolder(holder: PoemDetailsUiViewHolder, position: Int) {
        holder.bindData(currentList[position])
    }

}




class PoemDetailsUiViewHolder(
    private val viewBinding: ItemPoemBinding,
    private val itemClickListener: (PoemDetailsUi) -> Unit
) : RecyclerView.ViewHolder(viewBinding.root){

    init {
        with(viewBinding) {
        }
    }

    fun bindData(poem: PoemDetailsUi) {
        with(viewBinding) {
            root.setOnClickListener {
                itemClickListener(poem)
            }
            poemName.text = poem.shortTitle
        }
    }
}


object PoemDetailsUiDiffUtil : DiffUtil.ItemCallback<PoemDetailsUi>() {
    override fun areItemsTheSame(oldItem: PoemDetailsUi, newItem: PoemDetailsUi): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PoemDetailsUi, newItem: PoemDetailsUi): Boolean =
        oldItem == newItem
}
