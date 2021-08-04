package sphe.inews.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import sphe.inews.R
import sphe.inews.databinding.ItemCategoryBinding
import sphe.inews.databinding.ItemCountryBinding
import sphe.inews.enums.NewsCategory

class CategoryAdapter(private var list: Array<NewsCategory>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var listener: CategoryListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val viewBinder: ItemCategoryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category,
            parent,
            false
        )
       return ViewHolder(viewBinder)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]
        holder.image.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,category.image))
        holder.title.text = category.title
        holder.itemView.setOnClickListener {
            listener.onCategoryItemClick(category)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(@NonNull viewBinder: ItemCategoryBinding) : RecyclerView.ViewHolder(viewBinder.root) {
        var image: ShapeableImageView = viewBinder.image
        var title: TextView = viewBinder.title
    }

    fun setCountryClickListener(listener: CategoryListener){
        this.listener = listener
    }

    interface CategoryListener {
        fun onCategoryItemClick(category: NewsCategory)
    }
}