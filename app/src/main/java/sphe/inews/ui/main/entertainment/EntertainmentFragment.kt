package sphe.inews.ui.main.entertainment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_entertainment.*
import sphe.inews.R
import sphe.inews.models.Article
import sphe.inews.network.INewResource
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class EntertainmentFragment : Fragment() , ArticleAdapter.ArticleListener{

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: ArticleAdapter

    private lateinit var viewModel: EntertainmentViewModel

    private lateinit var mainContext : Context

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_entertainment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainContext = view.context

        recyclerView?.layoutManager = LinearLayoutManager(activity)

        adapter.setListener(this)

        viewModel = ViewModelProvider(this, providerFactory).get(EntertainmentViewModel::class.java)

        btn_retry.setOnClickListener {
            this.getEntertainmentNews()
        }
        this.setButtonRetryVisibility(false)
        this.setTextViewMessageVisibility(false)
        this.setShimmerLayoutVisibility(false)
        this.getEntertainmentNews()

    }


    override fun onArticleClicked(article: Article) {
        Toast.makeText(mainContext,""+article.publishedAt, Toast.LENGTH_SHORT).show()
    }

    private fun getEntertainmentNews(){
        viewModel.observeEntertainmentNews("za")?.removeObservers(viewLifecycleOwner)
        viewModel.observeEntertainmentNews("za")?.observe(viewLifecycleOwner, Observer { res ->

            when(res.status){
                INewResource.Status.LOADING -> {
                    this.setButtonRetryVisibility(false)
                    this.setTextViewMessageVisibility(false)
                    this.setShimmerLayoutVisibility(true)
                }
                INewResource.Status.ERROR -> {
                    this.setButtonRetryVisibility(true)
                    this.setTextViewMessageVisibility(true)
                    this.setShimmerLayoutVisibility(false)
                }
                INewResource.Status.SUCCESS -> {
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
