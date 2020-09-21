

package sphe.inews.ui.main.dialogfragments

import android.app.Activity
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
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
        const val TITLE = "title"
        const val CONTENT = "content"
        const val IMAGE = "imgUrl"
        const val DATE = "date"
        const val ARTICLE_URL = "articleUrl"
        const val SOURCE_NAME = "sourceName"
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

        arguments?.apply {
            txt_title.text = this.getString(TITLE,"")
            txt_content.text = this.getString(CONTENT,"No Article content available. Please click on read more to view the article.")
            txt_date.text = this.getString(DATE,"Date Unknown")?.let { Constants.appDateFormatArticle(it).toString() }
            txt_source.text = this.getString(SOURCE_NAME,"")
            Glide.with(header_image)
                .load(Uri.parse(""+arguments?.getString(IMAGE)))
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)
                .into(header_image)
            articleUrl = this.getString(ARTICLE_URL,"")
        }

        getDataObject()
    }

    @Suppress("RedundantOverride")
    override fun onStart() {
        super.onStart()



    }

    @Suppress("RedundantOverride")
    override fun onActivityCreated(args: Bundle?) {
        super.onActivityCreated(args)
    }

    private fun getDataObject(){
        val bookmark = requireArguments().getParcelable(BOOKMARK_OBJ) as? Bookmark
        if(bookmark != null){
            Log.d("@TAG","data : ${bookmark.title}")
        }else{
            Log.d("@TAG","bookmark is null")
        }
    }
}