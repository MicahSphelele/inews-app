package sphe.inews.ui.main.dialogfragments

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_view_article.*
import sphe.inews.R
import sphe.inews.util.Constants
import javax.inject.Inject


class ArticlePreviewDialogFragment @Inject constructor(): DaggerDialogFragment() {

    lateinit var articleUrl:String

    companion object{
        const val TITLE = "title"
        const val CONTENT = "content"
        const val IMAGE = "imgUrl"
        const val DATE = "date"
        const val ARTICLE_URL = "articleUrl"
        const val SOURCE_NAME = "sourceName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_article, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btn_exit.setOnClickListener {
            dismiss()
        }

        txt_read_more.setOnClickListener {
            Constants.launchCustomTabIntent(activity,articleUrl)
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



    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog
        val width = ViewGroup.LayoutParams.MATCH_PARENT
        val height = ViewGroup.LayoutParams.MATCH_PARENT

        dialog?.window.let {
            dialog?.window?.setLayout(width, height)
        }


    }

    override fun onActivityCreated(args: Bundle?) {
        super.onActivityCreated(args)
        dialog?.window?.let {
            dialog?.window?.attributes?.windowAnimations = R.style.FullScreenDialogStyle
        }
    }

}