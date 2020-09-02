package sphe.inews.ui.bottomsheet

import android.content.Context
import androidx.fragment.app.Fragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

 open class DaggerBottomSheetDialogFragment  @Inject constructor(): BottomSheetDialogFragment() , HasAndroidInjector{
    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)

    }
    override fun androidInjector(): AndroidInjector<Any> {
        @Suppress("UNCHECKED_CAST")
        return androidInjector as AndroidInjector<Any>
    }

}