package sphe.inews.presentation.ui.adapters

import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import sphe.inews.R
import sphe.inews.databinding.ItemCategoryBinding
import sphe.inews.domain.enums.NewsCategory
import sphe.inews.util.viewHolderItemBinding

class CategoryAdapter(private var list: Array<NewsCategory>) :
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var listener: CategoryListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewHolderItemBinding(R.layout.item_category) as ItemCategoryBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]

        holder.viewBinder.newsCategory = category

        holder.itemView.setOnClickListener {
            listener.onCategoryItemClick(category)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(@NonNull val viewBinder: ItemCategoryBinding) :
        RecyclerView.ViewHolder(viewBinder.root) {
        var image: ShapeableImageView = viewBinder.image
        var title: TextView = viewBinder.title
    }

    fun setCountryClickListener(listener: CategoryListener) {
        this.listener = listener
    }

    interface CategoryListener {
        fun onCategoryItemClick(category: NewsCategory)
    }
}