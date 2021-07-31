package sphe.inews.ui.main.bookmark

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import sphe.inews.R
import sphe.inews.databinding.FragmentBookmarkBinding
import sphe.inews.enums.NewsCategory
import sphe.inews.local.viewmodel.BookMarkViewModel
import sphe.inews.models.domain.ArticleBookmarkMapper
import sphe.inews.models.news.Article
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.ui.main.adapters.CategoryAdapter
import sphe.inews.util.AppLogger
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class BookmarkFragment : Fragment(R.layout.fragment_bookmark), ArticleAdapter.ArticleListener,
    CategoryAdapter.CategoryListener {

    private lateinit var binding: FragmentBookmarkBinding

    private val bookmarkViewModel by viewModels<BookMarkViewModel>()

    private lateinit var categoryBottomDialog: BottomSheetDialog

    @Inject
    lateinit var articleAdapter: ArticleAdapter

    @Inject
    lateinit var categoryAdapter: CategoryAdapter

    @Inject
    lateinit var mapper: ArticleBookmarkMapper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialElevationScale(/* growing= */ true)
    }

    @SuppressLint("RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBookmarkBinding.bind(view)

        hideShowLottieView(
            state = View.INVISIBLE
        )

        categoryBottomDialog = categoryBottomDialog()

        binding.recyclerView.let {
            it.apply {
                layoutManager = LinearLayoutManager(activity)
                adapter = articleAdapter
            }
        }


        binding.btnExit.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.imageFilter.setOnClickListener {
            categoryBottomDialog.show()
        }

        lifecycleScope.launch {
            try {
                val articles = mapper.toDomainList(bookmarkViewModel.getBooMarks())
                if (articles!!.isNotEmpty()) {
                    articleAdapter.setArticles(articles)
                    return@launch
                }
                hideShowLottieView(
                    state = View.VISIBLE,
                    animation = R.raw.animation_empty,
                    errorMsg = "No bookmarks found"
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
                hideShowLottieView(
                    state = View.VISIBLE,
                    errorMsg = "Something went wrong"
                )
                AppLogger.error("Error on fetching bookmark data", ex)
            }
        }

        articleAdapter.setListener(this)
        categoryAdapter.setCountryClickListener(this)
    }

    override fun onArticleClicked(article: Article, isVideo: Boolean) {
        Toast.makeText(requireContext(), "Feature coming in soon", Toast.LENGTH_SHORT).show()
    }

    override fun onShareClicked(article: Article) {
        Toast.makeText(requireContext(), "Feature coming in soon", Toast.LENGTH_SHORT).show()
    }

    override fun onCategoryItemClick(category: NewsCategory) {
        lifecycleScope.launch {
            try {
                hideShowLottieView(View.INVISIBLE)
                categoryBottomDialog.dismiss()
                articleAdapter.setArticles(mutableListOf())
                val articles = mapper.toDomainList(
                    bookmarkViewModel.getBooMarksByCategory(
                        category.title.toLowerCase(Locale.ROOT)
                    )
                )
                if (articles!!.isNotEmpty()) {
                    articleAdapter.setArticles(articles)
                    return@launch
                }
                hideShowLottieView(
                    state = View.VISIBLE,
                    animation = R.raw.animation_empty,
                    errorMsg = "No ${category.title} bookmarks found"
                )
            } catch (ex: Exception) {
                ex.printStackTrace()
                hideShowLottieView(
                    state = View.VISIBLE,
                    errorMsg = "Something went wrong"
                )
                AppLogger.error("Error on fetching bookmark data category: ${category.title}", ex)
            }
        }
    }

    private fun hideShowLottieView(
        state: Int,
        animation: Int = R.raw.animation_error,
        errorMsg: String = ""
    ) {
        binding.lottieView.visibility = state
        //Only play animation when view is visible
        if (state == View.VISIBLE) {
            binding.lottieView.setAnimation(animation)
            binding.lottieView.repeatCount = ValueAnimator.INFINITE
            binding.lottieView.playAnimation()
        } else {
            binding.lottieView.cancelAnimation()
        }
        binding.txtErrorMsg.visibility = state
        binding.txtErrorMsg.text = errorMsg
    }

    @SuppressLint("InflateParams")
    private fun categoryBottomDialog(): BottomSheetDialog {
        val bottomDialog = BottomSheetDialog(requireContext())
        bottomDialog.dismissWithAnimation = true
        bottomDialog.setCancelable(true)
        bottomDialog.dismissWithAnimation = true
        val v = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_category, null)
        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = categoryAdapter
        bottomDialog.setContentView(v)
        return bottomDialog
    }
}