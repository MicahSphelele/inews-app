package sphe.inews.ui.main.business

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
import kotlinx.android.synthetic.main.fragment_business.*
import sphe.inews.R
import sphe.inews.models.Article
import sphe.inews.network.INewResource
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class BusinessFragment : DaggerFragment(), ArticleAdapter.ArticleListener {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: ArticleAdapter

    lateinit var viewModel: BusinessViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_business, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView?.layoutManager = LinearLayoutManager(activity)
        adapter.setListener(this)

        viewModel = ViewModelProvider(this, providerFactory).get(BusinessViewModel::class.java)

        this.getBusinessNews()

    }

    override fun onArticleClicked(article: Article) {
        Toast.makeText(context,""+article.title,Toast.LENGTH_SHORT).show()
    }

    private fun getBusinessNews(){
        viewModel.observeBusinessNews()?.removeObservers(this)
        viewModel.observeBusinessNews()?.observe(viewLifecycleOwner, Observer { it ->
           when(it.status){
               INewResource.Status.LOADING -> {
                   Log.d("@MainActivity","Loading...")
               }
               INewResource.Status.ERROR -> {
                   Log.d("@MainActivity","Something went wrong")
               }
               INewResource.Status.SUCCESS -> {
                    recyclerView.adapter = adapter
                   adapter.setArticles(it.data?.articles)
               }

           }
       })
    }



}
