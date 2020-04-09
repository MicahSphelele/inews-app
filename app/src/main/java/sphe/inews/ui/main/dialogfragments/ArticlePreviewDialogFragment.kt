package sphe.inews.ui.main.dialogfragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.DialogFragment
import androidx.transition.ChangeBounds
import androidx.transition.Transition
import androidx.transition.TransitionManager
import dagger.android.support.DaggerDialogFragment
import kotlinx.android.synthetic.main.fragment_view_article.*
import sphe.inews.R
import javax.inject.Inject


class ArticlePreviewDialogFragment @Inject constructor(): DaggerDialogFragment() {


    private val constraintSetOld = ConstraintSet()
    private val constraintSetNew = ConstraintSet()
    private var altLayout = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_view_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        textView2.text = "llslsl sjsjjs sjsjs sjsjs sjsjs sjsjs sjsjs sjsjs sjsj chhwa akajja ajaikm sjjjs jwjw"
        constraintSetOld.clone(layout);
        constraintSetNew.clone(context, R.layout.joy)
        layout.setOnClickListener {
            val changeBounds: Transition = ChangeBounds()
            changeBounds.interpolator = OvershootInterpolator()

            TransitionManager.beginDelayedTransition(layout, changeBounds)

            altLayout = if (!altLayout) {
                constraintSetNew.applyTo(layout)
                true
            } else {
                constraintSetOld.applyTo(layout)
                false
            }
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