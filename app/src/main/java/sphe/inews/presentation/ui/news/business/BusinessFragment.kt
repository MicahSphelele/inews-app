package sphe.inews.presentation.ui.news.business

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
import sphe.inews.databinding.FragmentBusinessBinding
import sphe.inews.domain.enums.NewsCategory
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.domain.models.news.Article
import sphe.inews.domain.models.news.NewsResponse
import sphe.inews.domain.NetworkResult
import sphe.inews.presentation.ui.base.BaseActivity
import sphe.inews.presentation.ui.adapters.ArticleAdapter
import sphe.inews.presentation.ui.dialogfragments.ArticleViewFragment
import sphe.inews.presentation.ui.dialogfragments.ViewYoutubeDialogFragment
import sphe.inews.util.notNull
import sphe.inews.presentation.ui.news.NewsViewModel
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class BusinessFragment : Fragment(R.layout.fragment_business), ArticleAdapter.ArticleListener {

    private lateinit var viewYoutubeDialogFragment: ViewYoutubeDialogFragment
    private lateinit var binding: FragmentBusinessBinding

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

        binding = FragmentBusinessBinding.bind(view)

        binding.recyclerView.let {
            it.apply {
                layoutManager = LinearLayoutManager(activity)
            }
        }

        adapter.setListener(this)

        binding.btnRetry.setOnClickListener {
            getBusinessNews()
        }

        setErrorViewsVisibility(false)
        setShimmerLayoutVisibility(false)
        getBusinessNews()

    }

    override fun onArticleItemClick(article: Article, isVideo: Boolean) {
        when (isVideo) {
            true -> {
                val bundle = Bundle()
                viewYoutubeDialogFragment = ViewYoutubeDialogFragment()
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
                    ArticleViewFragment.BOOKMARK_OBJ,
                    Bookmark(
                        article.url,
                        article.author,
                        article.content,
                        article.description,
                        article.publishedAt,
                        sourceID,
                        article.source.name,
                        article.title,
                        article.urlToImage,
                        NewsCategory.BUSINESS.title.lowercase(Locale.ENGLISH)
                    )
                )

                findNavController().navigate(R.id.articlePreviewFragment, bundle, null, null)
            }
        }
    }

    override fun onShareItemClick(article: Article) {
        val shareIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, article.url)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, null))
    }

    private fun getBusinessNews() {
        liveData = newsViewModel.getNews("za", NewsCategory.BUSINESS.title.lowercase(Locale.ENGLISH))
        liveData.removeObservers(viewLifecycleOwner)
        liveData.observe(viewLifecycleOwner) {
            when (it) {
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
