package sphe.inews.ui.main.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import sphe.inews.R
import sphe.inews.enums.NewsCategory

class CategoryAdapter(private var list: Array<NewsCategory>) : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {

    private lateinit var listener: CategoryListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category = list[position]
        holder.image.setImageDrawable(ContextCompat.getDrawable(holder.itemView.context,category.image))
        holder.title.text = category.title
        holder.itemView.setOnClickListener {
            listener.onCategoryClicked(category)
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(@NonNull v: View) : RecyclerView.ViewHolder(v) {
        var image: ShapeableImageView = v.findViewById(R.id.image)
        var title: TextView = v.findViewById(R.id.title)
    }

    fun setCountryClickListener(listener: CategoryListener){
        this.listener = listener
    }

    interface CategoryListener {
        fun onCategoryClicked(category: NewsCategory)
    }
}