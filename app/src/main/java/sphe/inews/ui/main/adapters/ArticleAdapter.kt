package sphe.inews.ui.main.adapters

import android.view.ViewGroup
import android.widget.ImageButton
import androidx.annotation.NonNull
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.imageview.ShapeableImageView
import sphe.inews.R
import sphe.inews.databinding.ItemArticleBinding
import sphe.inews.models.news.Article
import sphe.inews.util.isYoutubeBoolean
import sphe.inews.util.viewHolderItemBinding

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {

    private var list: List<Article> = mutableListOf()

    private lateinit var listener: ArticleListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(parent.viewHolderItemBinding(R.layout.item_article) as ItemArticleBinding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val article = list[position]

        holder.viewBinder.article = article

        holder.image.setOnClickListener {
            listener.onArticleItemClick(article, isYoutubeBoolean(article.source.name))
        }

        holder.youtube.setOnClickListener {
            listener.onArticleItemClick(article, isYoutubeBoolean(article.source.name))
        }

        holder.share.setOnClickListener {
            listener.onShareItemClick(article)
        }
    }

    fun setArticles(list: List<Article>?) {
        this.list = list!!
        this.asyncListDiffer.submitList(this.list)
    }

    fun setListener(listener: ArticleListener) {
        this.listener = listener
    }

    fun emptyArticleList() {
        setArticles(mutableListOf())
    }

    class ViewHolder(@NonNull val viewBinder: ItemArticleBinding) :
        RecyclerView.ViewHolder(viewBinder.root) {
        var image: ShapeableImageView = viewBinder.image
        var share: ImageButton = viewBinder.share
        var youtube: ImageButton = viewBinder.youtube
    }

    private val diffCallBack = object : DiffUtil.ItemCallback<Article>() {
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }
    }

    private val asyncListDiffer = AsyncListDiffer(this, diffCallBack)

    interface ArticleListener {
        fun onArticleItemClick(article: Article, isVideo: Boolean)
        fun onShareItemClick(article: Article)
    }
}