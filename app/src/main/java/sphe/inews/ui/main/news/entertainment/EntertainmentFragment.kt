package sphe.inews.ui.main.news.entertainment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.FragmentEntertainmentBinding
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
class EntertainmentFragment : Fragment(), ArticleAdapter.ArticleListener {

    lateinit var viewYoutubeDialogFragment: ViewYoutubeDialogFragment

    @Inject
    lateinit var adapter: ArticleAdapter

    private val viewModel by viewModels<EntertainmentViewModel>()

    private lateinit var binding: FragmentEntertainmentBinding

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
        return inflater.inflate(R.layout.fragment_entertainment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentEntertainmentBinding.bind(view)

        binding.recyclerView.let {
            it.apply {
                layoutManager = LinearLayoutManager(activity)
            }
        }

        adapter.setListener(this)

        binding.btnRetry.setOnClickListener {
            this.getEntertainmentNews()
        }

        this.setErrorViewsVisibility(false)
        this.setShimmerLayoutVisibility(false)
        this.getEntertainmentNews()

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
                        Constants.ENTERTAINMENT
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

    private fun getEntertainmentNews() {
        viewModel.observeEntertainmentNews("za")?.let {
            viewModel.observeEntertainmentNews("za")?.removeObservers(this)
            viewModel.observeEntertainmentNews("za")?.observe(viewLifecycleOwner, { res ->

                when (res.status) {
                    Resources.Status.LOADING -> {
                        this.setErrorViewsVisibility(false)
                        this.setShimmerLayoutVisibility(true)
                    }
                    Resources.Status.ERROR -> {
                        this.setErrorViewsVisibility(true)
                        this.setShimmerLayoutVisibility(false)

                        context?.resources?.let {
                            binding.txtMessage.text = context?.resources?.getString(R.string.msg_error)
                        }
                    }
                    Resources.Status.SUCCESS -> {
                        this.setErrorViewsVisibility(false)
                        this.setShimmerLayoutVisibility(false)

                        binding.recyclerView.adapter = adapter
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
            binding.btnRetry.visibility = View.VISIBLE
            binding.txtMessage.visibility = View.VISIBLE
        } else {
            binding.btnRetry.visibility = View.GONE
            binding.txtMessage.visibility = View.GONE
        }
    }

    private fun setShimmerLayoutVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.shimmerViewContainer.startShimmer()
        } else {
            binding.shimmerViewContainer.visibility = View.GONE
            binding.shimmerViewContainer.stopShimmer()

        }
    }


}
