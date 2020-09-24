package sphe.inews.ui.main.news.technology

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_technology.*
import sphe.inews.R
import sphe.inews.models.Bookmark
import sphe.inews.models.news.Article
import sphe.inews.network.Resources
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.ui.main.dialogfragments.ArticlePreviewFragment
import sphe.inews.ui.main.dialogfragments.ViewYoutubeDialogFragment
import sphe.inews.util.notNull
import sphe.inews.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
class TechnologyFragment : DaggerFragment(), ArticleAdapter.ArticleListener {


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
    lateinit var articlePreviewDialogFragment:ArticlePreviewFragment


    private  val viewModel: TechnologyViewModel by lazy {
        ViewModelProvider(this, providerFactory).get(TechnologyViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(/* growing= */ false)
        enterTransition = MaterialElevationScale(/* growing= */ true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_technology, container, false)
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
            this.getTechnologyNews()
        }

        this.setErrorViewsVisibility(false)
        this.setShimmerLayoutVisibility(false)
        this.getTechnologyNews()
    }

    override fun onArticleClicked(article: Article,isVideo:Boolean) {
        when(isVideo){
            true ->{
                val bundle = Bundle()
                bundle.putString(ViewYoutubeDialogFragment.URL,article.url)
                viewYoutubeDialogFragment.arguments = bundle
                viewYoutubeDialogFragment.show((activity as  DaggerAppCompatActivity).supportFragmentManager,"viewYoutubeDialogFragment")
            }
            false ->{
                val bundle = Bundle()
                
                val sourceID = if(article.source.id.notNull()){

                    article.source.id.toString()

                }else{
                    "N/A"
                }

                bundle.putParcelable(ArticlePreviewFragment.BOOKMARK_OBJ,
                    Bookmark(0,article.url,article.author,
                        article.content,article.description,article.publishedAt,
                        sourceID,article.source.name,article.title,article.urlToImage))

                findNavController().navigate(R.id.articlePreviewFragment,bundle,null,null)
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

    private fun getTechnologyNews(){
        viewModel.observeTechnologyNews("za")?.let {

        }
        viewModel.observeTechnologyNews("za")?.removeObservers(this)
        viewModel.observeTechnologyNews("za")?.observe(viewLifecycleOwner, { res ->

            when(res.status){
                Resources.Status.LOADING -> {
                    this.setErrorViewsVisibility(false)
                    this.setShimmerLayoutVisibility(true)
                }
                Resources.Status.ERROR -> {
                    this.setErrorViewsVisibility(true)
                    this.setShimmerLayoutVisibility(false)

                    context?.resources?.let {
                        txt_message.text =  context?.resources?.getString(R.string.msg_error)
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
