package sphe.inews.ui.main.news.sport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_sport.*
import sphe.inews.R
import sphe.inews.models.news.Article
import sphe.inews.network.Resources
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.ui.main.dialogfragments.ArticlePreviewDialogFragment
import sphe.inews.ui.main.dialogfragments.ViewYoutubeDialogFragment
import sphe.inews.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SportFragment : DaggerFragment(), ArticleAdapter.ArticleListener {


    @Suppress("unused")
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: ArticleAdapter

    @Suppress("unused")
    @Inject
    lateinit var viewYoutubeDialogFragment: ViewYoutubeDialogFragment

    @Suppress("unused")
    @Inject
    lateinit var articlePreviewDialogFragment: ArticlePreviewDialogFragment

    private val viewModel: SportViewModel by lazy {
        ViewModelProvider(this, providerFactory).get(SportViewModel::class.java)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sport, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView?.let {
            recyclerView?.apply {
                layoutManager = LinearLayoutManager(activity)
            }
        }

        adapter.setListener(this)

        btn_retry.setOnClickListener {
            this.getSportNews()
        }

        this.setErrorViewsVisibility(false)
        this.setShimmerLayoutVisibility(false)
        this.getSportNews()
    }

    override fun onArticleClicked(article: Article,isVideo:Boolean) {
        when(isVideo){
            true ->{
                val bundle = Bundle()
                bundle.putString(ViewYoutubeDialogFragment.URL,article.url)
                viewYoutubeDialogFragment.arguments = bundle
                viewYoutubeDialogFragment.show((activity as DaggerAppCompatActivity).supportFragmentManager,"viewYoutubeDialogFragment")
            }
            false ->{
                val bundle = Bundle()
                bundle.putString(ArticlePreviewDialogFragment.TITLE,article.title)
                bundle.putString(ArticlePreviewDialogFragment.CONTENT,article.content)
                bundle.putString(ArticlePreviewDialogFragment.IMAGE,article.urlToImage)
                bundle.putString(ArticlePreviewDialogFragment.DATE,article.publishedAt)
                articlePreviewDialogFragment.arguments = bundle
                articlePreviewDialogFragment.show((activity as DaggerAppCompatActivity).supportFragmentManager,"articlePreviewDialogFragment")
            }
        }

    }

    override fun onShareClicked(article: Article) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, article.url)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    private fun getSportNews(){
        viewModel.observeSportNews("za")?.let {
            viewModel.observeSportNews("za")?.removeObservers(this)
            viewModel.observeSportNews("za")?.observe(viewLifecycleOwner, Observer {res ->

                when(res.status){
                    Resources.Status.LOADING -> {
                        Log.d("@Sport","LOADING...")
                        this.setErrorViewsVisibility(false)
                        this.setShimmerLayoutVisibility(true)
                    }
                    Resources.Status.ERROR -> {
                        Log.d("@Sport","ERROR...")
                        this.setErrorViewsVisibility(true)
                        this.setShimmerLayoutVisibility(false)

                        context?.resources?.let {
                            txt_message.text =  context?.resources?.getString(R.string.msg_error)
                        }
                    }
                    Resources.Status.SUCCESS -> {
                        Log.d("@Sport","SUCCESS...")
                        this.setErrorViewsVisibility(false)
                        this.setShimmerLayoutVisibility(false)
                        recyclerView.adapter = adapter
                        res.data?.let {
                            adapter.setArticles(res.data.articles)
                        }
                    }

                }
            })
        }

    }

    private fun setErrorViewsVisibility(isVisible:Boolean){
        if(isVisible){
            btn_retry.visibility = View.VISIBLE
            txt_message.visibility = View.VISIBLE
        }else{
            btn_retry.visibility = View.GONE
            txt_message.visibility = View.GONE
        }
    }


    private fun setShimmerLayoutVisibility(isVisible:Boolean){
        if(isVisible){
            shimmer_view_container.visibility = View.VISIBLE
            shimmer_view_container.startShimmer()
        }else{
            shimmer_view_container.visibility = View.GONE
            shimmer_view_container.stopShimmer()

        }
    }




}
