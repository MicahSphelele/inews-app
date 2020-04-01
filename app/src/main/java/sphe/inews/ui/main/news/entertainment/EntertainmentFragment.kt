package sphe.inews.ui.main.news.entertainment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_entertainment.*
import sphe.inews.R
import sphe.inews.models.news.Article
import sphe.inews.network.Resources
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class EntertainmentFragment : DaggerFragment() , ArticleAdapter.ArticleListener{

    @Suppress("unused")
    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: ArticleAdapter

    @Suppress("unused")
    private lateinit var mainContext : Context

    private  val viewModel: EntertainmentViewModel by lazy {
        ViewModelProvider(this, providerFactory).get(EntertainmentViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_entertainment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainContext = view.context

        recyclerView?.let {
            recyclerView?.apply {
                layoutManager = LinearLayoutManager(activity)
            }
        }

        adapter.setListener(this)



        btn_retry.setOnClickListener {
            this.getEntertainmentNews()
        }
        this.setErrorViewsVisibility(false)
        this.setShimmerLayoutVisibility(false)
        this.getEntertainmentNews()

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

    private fun getEntertainmentNews(){
        viewModel.observeEntertainmentNews("za")?.let {
            viewModel.observeEntertainmentNews("za")?.removeObservers(this)
            viewModel.observeEntertainmentNews("za")?.observe(viewLifecycleOwner, Observer { res ->

                when(res.status){
                    Resources.Status.LOADING -> {
                        this.setErrorViewsVisibility(false)
                        this.setShimmerLayoutVisibility(true)
                    }
                    Resources.Status.ERROR -> {
                        this.setErrorViewsVisibility(true)
                        this.setShimmerLayoutVisibility(false)

                        mainContext.resources?.let {
                            txt_message.text =  mainContext.resources.getString(R.string.msg_error)
                        }
                    }
                    Resources.Status.SUCCESS -> {
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
