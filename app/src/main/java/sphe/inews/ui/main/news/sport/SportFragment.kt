package sphe.inews.ui.main.news.sport

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_sport.*
import sphe.inews.R
import sphe.inews.models.news.Article
import sphe.inews.network.Resources
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class SportFragment : DaggerFragment(), ArticleAdapter.ArticleListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: ArticleAdapter

    private lateinit var viewModel: SportViewModel

    private lateinit var mainContext : Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_sport, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainContext = view.context

        recyclerView?.layoutManager = LinearLayoutManager(activity)
        adapter = ArticleAdapter()
        adapter.setListener(this)

        viewModel = ViewModelProvider(this, providerFactory).get(SportViewModel::class.java)

        btn_retry.setOnClickListener {
            this.getSportNews()
        }

        this.setButtonRetryVisibility(false)
        this.setTextViewMessageVisibility(false)
        this.setShimmerLayoutVisibility(false)
        this.getSportNews()
    }

    override fun onArticleClicked(article: Article) {
        Toast.makeText(mainContext,""+article.publishedAt, Toast.LENGTH_SHORT).show()
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
        viewModel.observeSportNews("za")?.removeObservers(this)
        viewModel.observeSportNews("za")?.observe(viewLifecycleOwner, Observer {res ->

            when(res.status){
                Resources.Status.LOADING -> {
                    Log.d("@Sport","LOADING...")
                    this.setButtonRetryVisibility(false)
                    this.setTextViewMessageVisibility(false)
                    this.setShimmerLayoutVisibility(true)
                }
                Resources.Status.ERROR -> {
                    Log.d("@Sport","ERROR...")
                    this.setButtonRetryVisibility(true)
                    this.setTextViewMessageVisibility(true)
                    this.setShimmerLayoutVisibility(false)
                }
                Resources.Status.SUCCESS -> {
                    Log.d("@Sport","SUCCESS...")
                    this.setButtonRetryVisibility(false)
                    this.setTextViewMessageVisibility(false)
                    this.setShimmerLayoutVisibility(false)
                    recyclerView.adapter = adapter
                    adapter.setArticles(res.data?.articles)
                }

            }

        })
    }

    private fun setButtonRetryVisibility(isVisible:Boolean){
        if(isVisible){
            btn_retry.visibility = View.VISIBLE
        }else{
            btn_retry.visibility = View.GONE
        }
    }

    private fun setTextViewMessageVisibility(isVisible:Boolean){
        if(isVisible){
            txt_message.visibility = View.VISIBLE
        }else{
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
