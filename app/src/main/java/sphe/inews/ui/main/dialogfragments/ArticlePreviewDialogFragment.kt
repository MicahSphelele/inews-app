

package sphe.inews.ui.main.dialogfragments

import android.app.Activity
import android.app.Dialog
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_view_article.*
import sphe.inews.R
import sphe.inews.ui.bottomsheet.DaggerBottomSheetDialogFragment
import sphe.inews.util.Constants
import javax.inject.Inject


class ArticlePreviewDialogFragment @Inject constructor(): DaggerBottomSheetDialogFragment() {

    lateinit var articleUrl:String

    companion object{
        const val TITLE = "title"
        const val CONTENT = "content"
        const val IMAGE = "imgUrl"
        const val DATE = "date"
        const val ARTICLE_URL = "articleUrl"
        const val SOURCE_NAME = "sourceName"
    }

    @Suppress("RedundantOverride")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupFullHeight(bottomSheetDialog,BottomSheetBehavior.STATE_EXPANDED)
        }
        dialog.setOnDismissListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupFullHeight(bottomSheetDialog,BottomSheetBehavior.STATE_COLLAPSED)
        }
        return dialog
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

    }

    @Suppress("RedundantOverride")
    override fun onStart() {
        super.onStart()
//        val dialog = dialog
//        val width = ViewGroup.LayoutParams.MATCH_PARENT
//        val height = ViewGroup.LayoutParams.MATCH_PARENT
//
//        dialog?.window.let {
//            dialog?.window?.setLayout(width, height)
//        }


    }

    @Suppress("RedundantOverride")
    override fun onActivityCreated(args: Bundle?) {
        super.onActivityCreated(args)
//        dialog?.window?.let {
//            dialog?.window?.attributes?.windowAnimations = R.style.FullScreenDialogStyle
//        }
    }

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog, state : Int){
        val bottomSheet: FrameLayout = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet) as FrameLayout
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight:Int = getWindowHeight()
        layoutParams.height = windowHeight
        bottomSheet.layoutParams = layoutParams
        behavior.state = state
    }

    private fun getWindowHeight() : Int{
        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}