package sphe.inews.ui.main.news.sport

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_sport.*
import sphe.inews.R
import sphe.inews.models.Bookmark
import sphe.inews.models.news.Article
import sphe.inews.network.Resources
import sphe.inews.ui.BaseActivity
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.ui.main.dialogfragments.ArticlePreviewFragment
import sphe.inews.ui.main.dialogfragments.ViewYoutubeDialogFragment
import sphe.inews.util.Constants
import sphe.inews.util.notNull
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class SportFragment : Fragment(), ArticleAdapter.ArticleListener {

    lateinit var viewYoutubeDialogFragment: ViewYoutubeDialogFragment

    lateinit var articlePreviewDialogFragment: ArticlePreviewFragment

    @Inject
    lateinit var adapter: ArticleAdapter

    private val viewModel by viewModels<SportViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(/* growing= */ false)
        enterTransition = MaterialElevationScale(/* growing= */ true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    override fun onArticleClicked(article: Article, isVideo: Boolean) {
        when (isVideo) {
            true -> {
                viewYoutubeDialogFragment = ViewYoutubeDialogFragment()
                val bundle = Bundle()
                bundle.putString(ViewYoutubeDialogFragment.URL, article.url)
                viewYoutubeDialogFragment.arguments = bundle
                viewYoutubeDialogFragment.show(
                    (activity as BaseActivity).supportFragmentManager,
                    "viewYoutubeDialogFragment"
                )
            }
            false -> {
                val bundle = Bundle()

                val sourceID = if (article.source.id.notNull()) {

                    article.source.id.toString()

                } else {
                    "N/A"
                }

                bundle.putParcelable(
                    ArticlePreviewFragment.BOOKMARK_OBJ,
                    Bookmark(
                        0,
                        article.url,
                        article.author,
                        article.content,
                        article.description,
                        article.publishedAt,
                        sourceID,
                        article.source.name,
                        article.title,
                        article.urlToImage,
                        Constants.SPORTS
                    )
                )

                findNavController().navigate(R.id.articlePreviewFragment, bundle, null, null)
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

    private fun getSportNews() {
        viewModel.observeSportNews("za")?.let {
            viewModel.observeSportNews("za")?.removeObservers(this)
            viewModel.observeSportNews("za")?.observe(viewLifecycleOwner, { res ->

                when (res.status) {
                    Resources.Status.LOADING -> {
                        Log.d("@Sport", "LOADING...")
                        this.setErrorViewsVisibility(false)
                        this.setShimmerLayoutVisibility(true)
                    }
                    Resources.Status.ERROR -> {
                        Log.d("@Sport", "ERROR...")
                        this.setErrorViewsVisibility(true)
                        this.setShimmerLayoutVisibility(false)

                        context?.resources?.let {
                            txt_message.text = context?.resources?.getString(R.string.msg_error)
                        }
                    }
                    Resources.Status.SUCCESS -> {
                        Log.d("@Sport", "SUCCESS...")
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

    private fun setErrorViewsVisibility(isVisible: Boolean) {
        if (isVisible) {
            btn_retry.visibility = View.VISIBLE
            txt_message.visibility = View.VISIBLE
        } else {
            btn_retry.visibility = View.GONE
            txt_message.visibility = View.GONE
        }
    }


    private fun setShimmerLayoutVisibility(isVisible: Boolean) {
        if (isVisible) {
            shimmer_view_container.visibility = View.VISIBLE
            shimmer_view_container.startShimmer()
        } else {
            shimmer_view_container.visibility = View.GONE
            shimmer_view_container.stopShimmer()

        }
    }


}
