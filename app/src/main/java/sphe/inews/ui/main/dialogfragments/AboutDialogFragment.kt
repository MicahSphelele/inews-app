package sphe.inews.ui.main.dialogfragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.DialogFragment
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_about.*
import sphe.inews.R
import sphe.inews.util.Constants
import javax.inject.Inject
import javax.inject.Named

class AboutDialogFragment @Inject constructor(): DaggerDialogFragment() {


    @Inject
    @Named(Constants.NAMED_APP_VERSION)
    lateinit var appVersion: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
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

        txt_app_version.text = appVersion

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