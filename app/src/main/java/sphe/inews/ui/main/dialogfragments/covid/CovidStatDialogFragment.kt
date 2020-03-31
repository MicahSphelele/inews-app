package sphe.inews.ui.main.dialogfragments.covid

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import dagger.android.support.DaggerDialogFragment
import sphe.inews.R
import sphe.inews.network.Resources
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.viewmodels.ViewModelProviderFactory
import javax.inject.Inject

class CovidStatDialogFragment @Inject constructor(): DaggerDialogFragment()  {

    @Inject
    lateinit var providerFactory: ViewModelProviderFactory

    @Inject
    lateinit var adapter: ArticleAdapter

   companion object {
       const val TAG = "@CovidStatDialog"
    }

    private lateinit var viewModel: Covid19StatsViewModel

    private lateinit var mainContext : Context

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_covid19_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainContext = view.context
        viewModel = ViewModelProvider(this, providerFactory).get(Covid19StatsViewModel::class.java)
        this.getCovid19Stats()
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

    private fun getCovid19Stats(){

        viewModel.observeCovid19Data("South Africa")?.removeObservers(this)
        viewModel.observeCovid19Data("South Africa")?.observe(viewLifecycleOwner, Observer {res ->

            when(res.status){
                Resources.Status.LOADING ->{
                    Log.d(TAG,"LOADING...")
                }
                Resources.Status.ERROR ->{
                    Log.d(TAG,"ERROR...")
                }
                Resources.Status.SUCCESS ->{
                    Log.d(TAG,"SUCCESS...${res.data?.latestStatByCountry.toString()}")

                }

            }

        })
    }
}