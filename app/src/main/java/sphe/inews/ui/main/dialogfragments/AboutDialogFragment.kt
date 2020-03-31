package sphe.inews.ui.main.dialogfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_about.*
import sphe.inews.R
import javax.inject.Inject

class AboutDialogFragment @Inject constructor(): DaggerDialogFragment() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?,savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        txt_app_version.text = view.context.packageManager.getPackageInfo(view.context.packageName,0).versionName

        btn_close.setOnClickListener{
            dismiss()
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
        //dialog!!.window?.getAttributes()?.windowAnimations = R.style.FullScreenDialogStyle
        dialog?.window?.let {
            dialog?.window?.attributes?.windowAnimations = R.style.FullScreenDialogStyle
        }
    }
}