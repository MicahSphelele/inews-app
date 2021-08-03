package sphe.inews.ui.main.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import sphe.inews.R
import sphe.inews.models.news.Article
import sphe.inews.util.AppLogger

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {


    private var list: List<Article> = mutableListOf()

    private lateinit var listener: ArticleListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val article = list[position]
        val isVideo: Boolean

        @Suppress("SENSELESS_COMPARISON")
        if (article.urlToImage != null) {
            Glide.with(holder.itemView.context)
                .load(Uri.parse(article.urlToImage))
                .placeholder(R.drawable.logo)
                .error(R.drawable.logo)
                .into(holder.image)
        }

        holder.source.text = article.source.name

        val arrayTitle = article.title.split("-")

        if (arrayTitle.size > 1) {
            holder.title.text = article.title.split("-")[0]
        } else {
            holder.title.text = article.title
        }

        if (article.source.name == "Youtube.com") {

            holder.youtube.visibility = View.VISIBLE
            isVideo = true

        } else {
            holder.youtube.visibility = View.GONE
            isVideo = false
        }

        holder.image.setOnClickListener {
            listener.onArticleItemClick(article, isVideo)
        }

        holder.youtube.setOnClickListener {
            listener.onArticleItemClick(article, isVideo)
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

    class ViewHolder(@NonNull v: View) : RecyclerView.ViewHolder(v) {
        var image: ShapeableImageView = v.findViewById(R.id.image)
        var title: TextView = v.findViewById(R.id.title)
        var source: TextView = v.findViewById(R.id.source)
        var share: ImageButton = v.findViewById(R.id.share)
        var youtube: ImageButton = v.findViewById(R.id.youtube)

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