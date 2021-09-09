package sphe.inews.ui.main.news.technology

import android.content.Intent
import android.os.Bundle
import android.util.Log
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
import sphe.inews.databinding.FragmentTechnologyBinding
import sphe.inews.domain.enums.NewsCategory
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.domain.models.news.Article
import sphe.inews.domain.models.news.NewsResponse
import sphe.inews.network.results.NetworkResult
import sphe.inews.ui.BaseActivity
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.ui.main.dialogfragments.ArticleViewFragment
import sphe.inews.ui.main.dialogfragments.ViewYoutubeDialogFragment
import sphe.inews.util.notNull
import sphe.inews.viewmodels.NewsViewModel
import java.util.*
import javax.inject.Inject

/**
 * A simple [Fragment] subclass.
 */
@AndroidEntryPoint
class TechnologyFragment : Fragment(R.layout.fragment_technology), ArticleAdapter.ArticleListener {

    private lateinit var viewYoutubeDialogFragment: ViewYoutubeDialogFragment
    private lateinit var binding: FragmentTechnologyBinding

    @Inject
    lateinit var adapter: ArticleAdapter

    private val newsViewModel by viewModels<NewsViewModel>()
    private var liveData: LiveData<NetworkResult<NewsResponse>> = MediatorLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(/* growing= */ false)
        enterTransition = MaterialElevationScale(/* growing= */ true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTechnologyBinding.bind(view)

        binding.recyclerView.let {
            it.apply {
                layoutManager = LinearLayoutManager(activity)
            }
        }

        adapter.setListener(this)

        binding.btnRetry.setOnClickListener {
            getTechnologyNews()
        }

        setErrorViewsVisibility(false)
        setShimmerLayoutVisibility(false)
        getTechnologyNews()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i("@TAG", "onDestroy")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i("@TAG", "onDestroyView")
    }

    override fun onArticleItemClick(article: Article, isVideo: Boolean) {
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
                        NewsCategory.TECHNOLOGY.title.toLowerCase(Locale.ENGLISH)
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

    private fun getTechnologyNews() {
        liveData = newsViewModel.getNews("za", NewsCategory.TECHNOLOGY.title.toLowerCase(Locale.ENGLISH))
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
