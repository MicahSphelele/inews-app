

package sphe.inews.ui.main.dialogfragments

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.google.android.material.transition.MaterialElevationScale
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_view_article.*
import sphe.inews.R
import sphe.inews.models.Bookmark
import sphe.inews.util.Constants
import javax.inject.Inject


class ArticlePreviewFragment @Inject constructor(): DaggerFragment() {

    private lateinit var articleUrl: String

    companion object{
        const val BOOKMARK_OBJ = "bookmarkObject"
    }

    @Suppress("RedundantOverride")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialElevationScale(/* growing= */ true)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_article, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_exit.setOnClickListener {
            findNavController().navigateUp()
        }

        txt_read_more.setOnClickListener {
            Constants.launchCustomTabIntent(activity as Activity,articleUrl)
        }

        setUpUIData()
    }

    @Suppress("RedundantOverride")
    override fun onStart() {
        super.onStart()
    }

    @Suppress("RedundantOverride")
    override fun onActivityCreated(args: Bundle?) {
        super.onActivityCreated(args)
    }

    private fun setUpUIData(){
        requireArguments().apply {
            val bookmark = requireArguments().getParcelable(BOOKMARK_OBJ) as? Bookmark

            txt_title.text = if(bookmark?.title == "" || bookmark?.title == null){
                "No Title"
            }else{
                bookmark.title
            }
            txt_content.text = if(bookmark?.content == "" || bookmark?.content == null){
                "No Article content available. Please click on read more to view the article."
            }else{
                bookmark.content
            }
            txt_date.text = if(bookmark?.publishedAt == "" || bookmark?.publishedAt==null){
                "Date Unknown"
            }else{
                bookmark.publishedAt.let {
                    Constants.appDateFormatArticle(it!!).toString()
                }
            }
            txt_source.text = if(bookmark?.sourceName == "" || bookmark?.sourceName == null){
                "No Source"
            }else{
                bookmark.sourceName
            }

            val imageUrl = if (bookmark?.urlToImage == "" || bookmark?.urlToImage == null){
                "null"
            }else{
                bookmark.urlToImage
            }
            Glide.with(header_image)
                .load(Uri.parse(imageUrl))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(header_image)

            articleUrl = bookmark?.url!!

        }
    }
}