package sphe.inews.ui.main.news.health

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.FragmentHealthBinding
import sphe.inews.models.Bookmark
import sphe.inews.models.news.Article
import sphe.inews.models.news.NewsResponse
import sphe.inews.network.response.NetworkResult
import sphe.inews.ui.BaseActivity
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.ui.main.dialogfragments.ArticlePreviewFragment
import sphe.inews.ui.main.dialogfragments.ViewYoutubeDialogFragment
import sphe.inews.util.Constants
import sphe.inews.util.notNull
import sphe.inews.viewmodels.NewsViewModel
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class HealthFragment : Fragment(R.layout.fragment_health), ArticleAdapter.ArticleListener {

    private lateinit var viewYoutubeDialogFragment: ViewYoutubeDialogFragment
    private lateinit var binding: FragmentHealthBinding

    private val newsViewModel by viewModels<NewsViewModel>()
    private var liveData: LiveData<NetworkResult<NewsResponse>> = MediatorLiveData()

    @Inject
    lateinit var adapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(/* growing= */ false)
        enterTransition = MaterialElevationScale(/* growing= */ true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHealthBinding.bind(view)

        binding.recyclerView.let {
            it.apply {
                layoutManager = LinearLayoutManager(activity)
            }
        }

        adapter.setListener(this)

        binding.btnRetry.setOnClickListener {
            getHealthNews()
        }

        setErrorViewsVisibility(false)
        setShimmerLayoutVisibility(false)
        getHealthNews()

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
                        Constants.HEALTH
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

    private fun getHealthNews() {
        liveData = newsViewModel.getNews("za", Constants.HEALTH)
        liveData.removeObservers(viewLifecycleOwner)
        liveData.observe(viewLifecycleOwner) {
            when(it) {
                is NetworkResult.Loading -> {
                    setErrorViewsVisibility(false)
                    setShimmerLayoutVisibility(true)
                }
                is NetworkResult.Error -> {
                    setErrorViewsVisibility(true)
                    setShimmerLayoutVisibility(false)
                    context?.resources?.let {
                        binding.txtMessage.text = context?.resources?.getString(R.string.msg_error)
                    }
                }
                is NetworkResult.Success -> {
                    setErrorViewsVisibility(false)
                    setShimmerLayoutVisibility(false)
                    binding.recyclerView.adapter = adapter
                    it.data?.let { results ->
                        adapter.setArticles(results.articles)
                    }
                }
            }
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
