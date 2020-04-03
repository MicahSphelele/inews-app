package sphe.inews.ui.main.adapters

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import sphe.inews.R
import sphe.inews.models.news.Article

class ArticleAdapter : RecyclerView.Adapter<ArticleAdapter.ViewHolder>() {


    private lateinit var list: List<Article>

    private lateinit var listener: ArticleListener

     override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

         return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false))
     }

     override fun getItemCount(): Int {
         return list.size
     }

     override fun onBindViewHolder(holder: ViewHolder, position: Int) {
         val article = list[position]
         var isVideo = false
         @Suppress("SENSELESS_COMPARISON")
         if(article.urlToImage != null){
             Glide.with(holder.itemView.context)
                 .load(Uri.parse(article.urlToImage))
                 .placeholder(R.mipmap.ic_launcher)
                 .error(R.mipmap.ic_launcher)
                 .into(holder.image)
         }

         holder.source.text = article.source.name

         val  arrayTitle = article.title.split("-")

         if(arrayTitle.size>1){
             holder.title.text = article.title.split("-")[0]
         }else{
             holder.title.text = article.title
         }

         if(article.source.name == "Youtube.com"){

             holder.youtube.visibility = View.VISIBLE
             isVideo = true

         }else{
             holder.youtube.visibility = View.GONE
             isVideo = false
         }

         holder.title.setOnClickListener{
             if(isVideo){
                 listener.onArticleClicked(article,true)
             }else{
                 listener.onArticleClicked(article,false)
             }

         }

         holder.youtube.setOnClickListener{
             listener.onArticleClicked(article,true)
         }

         holder.share.setOnClickListener{
             listener.onShareClicked(article)
         }



     }

    fun setArticles(list:List<Article>?) {
        this.list = list!!
        notifyDataSetChanged()
    }

    fun setListener(listener:ArticleListener){
        this.listener = listener
    }

    class ViewHolder(@NonNull v: View):RecyclerView.ViewHolder(v){
          var image : ImageView = v.findViewById(R.id.image)
          var title : TextView = v.findViewById(R.id.title)
          var source : TextView = v.findViewById(R.id.source)
          var share : ImageButton = v.findViewById(R.id.share)
          var youtube: ImageButton = v.findViewById(R.id.youtube)

     }

    interface ArticleListener{
        fun onArticleClicked(article: Article,isVideo:Boolean)
        fun onShareClicked(article: Article)
    }
 }