package sphe.inews.ui.main.dialogfragments

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.widget.FrameLayout
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.content.ContextCompat
import androidx.transition.TransitionManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.FragmentAboutBinding
import sphe.inews.util.Constants
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class AboutDialogFragment : BottomSheetDialogFragment() {

    @Inject
    @Named(Constants.NAMED_APP_VERSION)
    lateinit var appVersion: String

    @Inject
    @Named(Constants.NAMED_SET_OLD)
    lateinit var constraintSetOld: ConstraintSet

    @Inject
    @Named(Constants.NAMED_SET_NEW)
    lateinit var constraintSetNew: ConstraintSet

    private var altLayout: Boolean = false

    private lateinit var binding: FragmentAboutBinding

    @Suppress("RedundantOverride")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog: Dialog = super.onCreateDialog(savedInstanceState)
        dialog.setOnShowListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupFullHeight(bottomSheetDialog, BottomSheetBehavior.STATE_EXPANDED)
        }
        dialog.setOnDismissListener {
            val bottomSheetDialog = it as BottomSheetDialog
            setupFullHeight(bottomSheetDialog, BottomSheetBehavior.STATE_COLLAPSED)
        }
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_about, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAboutBinding.bind(view)

        (binding.toolbar as Toolbar).navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_action_home)

        (binding.toolbar as Toolbar).setNavigationOnClickListener {
            dismiss()
        }

        constraintSetOld.clone(binding.layoutAbout)
        constraintSetNew.clone(context, R.layout.fragment_about_2)

        binding.card.setOnClickListener {
            performLayoutTransition()
        }
        //Github
        binding.cardBtn1.setOnClickListener {
            Constants.launchCustomTabIntent(
                activity as Activity,
                resources.getString(R.string.url_github)
            )
        }
        //Linkedin
        binding.cardBtn2.setOnClickListener {
            Constants.launchCustomTabIntent(
                activity as Activity,
                resources.getString(R.string.url_linkedin)
            )
        }
        //Gmail
        binding.cardBtn3.setOnClickListener {
            val intent = Intent(Intent.ACTION_SENDTO).apply {
                type = "text/plain"
                data = Uri.parse("mailto:${resources.getString(R.string.url_gmail)}")
                putExtra(Intent.EXTRA_EMAIL, resources.getString(R.string.url_gmail))
                putExtra(Intent.EXTRA_SUBJECT, "Hi Sphelele")
            }

            if (intent.resolveActivity(requireActivity().packageManager) != null) {
                startActivity(intent)
            } else {
                Toast.makeText(activity, "Application not found", Toast.LENGTH_SHORT).show()
            }
        }
        //Instagram
        binding.cardBtn4.setOnClickListener {
            Constants.launchCustomTabIntent(
                activity as Activity,
                resources.getString(R.string.url_instagram)
            )
        }

        binding.txtAppVersion.text = appVersion

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

    private fun setupFullHeight(bottomSheetDialog: BottomSheetDialog, state: Int) {
        val bottomSheet: FrameLayout =
            bottomSheetDialog.findViewById<FrameLayout>(R.id.design_bottom_sheet) as FrameLayout
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet.layoutParams
        val windowHeight: Int = getWindowHeight()
        layoutParams.height = windowHeight
        bottomSheet.layoutParams = layoutParams
        behavior.state = state
    }

    @Suppress("DEPRECATION")
    private fun getWindowHeight(): Int {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {

            val windowMetrics = requireActivity().windowManager.currentWindowMetrics
            val insets = windowMetrics.windowInsets
                .getInsetsIgnoringVisibility(WindowInsets.Type.systemBars())
            return windowMetrics.bounds.height() - insets.top - insets.bottom
        }

        val displayMetrics = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun performLayoutTransition() {
        Log.i(Constants.DEBUG_TAG,"Clone")
        TransitionManager.beginDelayedTransition(binding.layoutAbout)
        altLayout = if (!altLayout) {
            constraintSetNew.applyTo(binding.layoutAbout)
            true
        } else {
            constraintSetOld.applyTo(binding.layoutAbout)
            false
        }
    }
}