package sphe.inews.ui.main.dialogfragments

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.fragment_about.*
import kotlinx.android.synthetic.main.fragment_about.toolbar
import kotlinx.android.synthetic.main.fragment_about.txt_app_version
import kotlinx.android.synthetic.main.fragment_about_2.*
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