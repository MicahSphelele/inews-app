package sphe.inews.ui.main.news.business

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
import sphe.inews.databinding.FragmentBusinessBinding
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
class BusinessFragment : Fragment(R.layout.fragment_business), ArticleAdapter.ArticleListener {

    lateinit var viewYoutubeDialogFragment: ViewYoutubeDialogFragment

    private val viewModel by viewModels<BusinessViewModel>()

    private lateinit var binding: FragmentBusinessBinding

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
            this.getBusinessNews()
        }

        this.setErrorViewsVisibility(false)
        this.setShimmerLayoutVisibility(false)
        this.getBusinessNews()

    }

    override fun onArticleClicked(article: Article, isVideo: Boolean) {
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
                        Constants.BUSINESS
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

    private fun getBusinessNews() {
        viewModel.observeBusinessNews("za")?.let {
            viewModel.observeBusinessNews("za")?.removeObservers(this)
            viewModel.observeBusinessNews("za")?.observe(viewLifecycleOwner, { res ->
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
