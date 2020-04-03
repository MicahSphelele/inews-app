package sphe.inews.ui.main.dialogfragments

import android.os.Bundle
import androidx.fragment.app.DialogFragment
import dagger.android.support.DaggerDialogFragment
import sphe.inews.R
import javax.inject.Inject

class ViewYoutubeDialogFragment @Inject constructor(): DaggerDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }
}