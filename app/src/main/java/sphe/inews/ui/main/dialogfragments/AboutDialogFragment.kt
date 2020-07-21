package sphe.inews.ui.main.dialogfragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_about.toolbar
import kotlinx.android.synthetic.main.fragment_about.txt_app_version
import kotlinx.android.synthetic.main.fragment_about_2.card
import kotlinx.android.synthetic.main.fragment_about_2.layout_about
import sphe.inews.R
import sphe.inews.ui.bottomsheet.DaggerBottomSheetDialogFragment
import sphe.inews.util.Constants
import javax.inject.Inject
import javax.inject.Named

class AboutDialogFragment @Inject constructor(): DaggerBottomSheetDialogFragment() {

    @Inject
    @Named(Constants.NAMED_APP_VERSION)
    lateinit var appVersion: String

    @Inject
    @Named(Constants.NAMED_SET_OLD)
    lateinit var constraintSetOld: ConstraintSet

    @Inject
    @Named(Constants.NAMED_SET_NEW)
    lateinit var constraintSetNew: ConstraintSet

    var altLayout:Boolean = false

    @Suppress("RedundantOverride")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog:Dialog = super.onCreateDialog(savedInstanceState)
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

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //imageDrop = view.findViewById(R.id.imgController)
        context?.resources?.let {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                (toolbar as Toolbar).navigationIcon = context?.resources?.getDrawable(R.drawable.ic_action_home,null)
            }else{
                @Suppress("DEPRECATION")
                (toolbar as Toolbar).navigationIcon = context?.resources?.getDrawable(R.drawable.ic_action_home)
            }
        }
        (toolbar as Toolbar).setNavigationOnClickListener{
            dismiss()
        }

        constraintSetOld.clone(layout_about)
        constraintSetNew.clone(context,R.layout.fragment_about_2)
        
        card.setOnClickListener {
            performLayoutTransition()
        }
        //Github
        card_btn_1.setOnClickListener {
            Constants.launchCustomTabIntent(activity as Activity,resources.getString(R.string.url_github))
        }
        //Linkedin
        card_btn_2.setOnClickListener {
            Constants.launchCustomTabIntent(activity as Activity,resources.getString(R.string.url_linkedin))
        }
        //Gmail
        card_btn_3.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO)
            intent.type="text/plain"
            intent.data = Uri.parse("mailto:${resources.getString(R.string.url_gmail)}")
            intent.putExtra(Intent.EXTRA_EMAIL,resources.getString(R.string.url_gmail))
            intent.putExtra(Intent.EXTRA_SUBJECT,"Hi Sphelele")
            if(intent.resolveActivity(activity!!.packageManager)!=null){
                startActivity(intent)
            }else{
                Toast.makeText(activity, "Application not found", Toast.LENGTH_SHORT).show()
            }
        }
        //Instagram
        card_btn_4.setOnClickListener {
            Constants.launchCustomTabIntent(activity as Activity,resources.getString(R.string.url_instagram))
        }

        txt_app_version.text = appVersion

    }

    @Suppress("RedundantOverride")
    override fun onStart() {
        super.onStart()
//        val dialog = dialog
//        val width = ViewGroup.LayoutParams.MATCH_PARENT
//        val height = ViewGroup.LayoutParams.MATCH_PARENT
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
        val bottomSheet:FrameLayout = bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet) as FrameLayout
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

    private fun performLayoutTransition(){
        TransitionManager.beginDelayedTransition(layout_about)
        altLayout = if(!altLayout){
            constraintSetNew.applyTo(layout_about)
            true
        }else{
            constraintSetOld.applyTo(layout_about)
            false
        }
    }
}