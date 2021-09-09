package sphe.inews.ui.main.dialogfragments

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sphe.inews.R
import sphe.inews.databinding.FragmentViewArticleBinding
import sphe.inews.domain.models.bookmark.ArticleBookmarkMapper
import sphe.inews.local.viewmodel.BookMarkViewModel
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.util.Constants
import sphe.inews.util.notNull
import javax.inject.Inject

@AndroidEntryPoint
class ArticleViewFragment : Fragment(R.layout.fragment_view_article) {

    private lateinit var articleUrl: String

    companion object {
        const val BOOKMARK_OBJ = "bookmarkObject"
    }

    @Inject
    lateinit var mapper: ArticleBookmarkMapper

    private val viewModel by viewModels<BookMarkViewModel>()

    private lateinit var binding: FragmentViewArticleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialElevationScale(/* growing= */ true)

    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentViewArticleBinding.bind(view)

        binding.btnExit.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.btnReadMore.setOnClickListener {
            Constants.launchCustomTabIntent(activity as Activity, articleUrl)
        }

        setUpUIData()
    }

    private fun setUpUIData() {

        val bookmarkArticle = requireArguments().getParcelable(BOOKMARK_OBJ) as? Bookmark

        bookmarkArticle?.let {
            binding.article = mapper.mapToDomainModel(it)
            articleUrl = it.url
        }

         if (bookmarkArticle?.title == "" || bookmarkArticle?.title == null) {
            bookmarkArticle?.title = "No Title"
        }

        if (bookmarkArticle?.content == "" || bookmarkArticle?.content == null) {
            bookmarkArticle?.content = "No Article content available. Please click on read more to view the article."
        }

        if (bookmarkArticle?.publishedAt == "" || bookmarkArticle?.publishedAt == null) {
            bookmarkArticle?.publishedAt = "Date Unknown"
        }

        if (bookmarkArticle?.sourceName == "" || bookmarkArticle?.sourceName == null) {
            bookmarkArticle?.sourceName = "No Source"
        }

        if (bookmarkArticle?.urlToImage == "" || bookmarkArticle?.urlToImage == null) {
            bookmarkArticle?.urlToImage = "null"
        }

        lifecycleScope.launch {

            val savedBookmark = viewModel.getBooMark(articleUrl)

            binding.checkBookmark.isChecked = savedBookmark.notNull()

            binding.checkBookmark.setOnCheckedChangeListener { _, checked ->
                lifecycleScope.launch {
                    if (checked) {
                        bookmarkArticle?.let {
                            viewModel.insert(it)
                        }

                    } else {
                        savedBookmark?.let {
                            viewModel.delete(it)
                        }
                    }
                }
            }
        }
    }
}