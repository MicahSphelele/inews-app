package sphe.inews.ui.main.dialogfragments.covid

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_covid19_stats.*
import sphe.inews.R
import sphe.inews.models.Country
import sphe.inews.models.covid.LatestStatByCountry
import sphe.inews.network.Resources
import sphe.inews.ui.main.adapters.CountryAdapter
import sphe.inews.util.Constants
import javax.inject.Inject

@AndroidEntryPoint
class CovidStatDialogFragment  : DialogFragment(), CountryAdapter.CountryListener  {

    @Inject
    lateinit var adapter: CountryAdapter

    private lateinit var countryBottomDialog: BottomSheetDialog

    private val viewModel by viewModels<Covid19StatsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_covid19_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (toolbar as Toolbar).navigationIcon = ContextCompat.getDrawable(requireContext(),R.drawable.ic_action_home)

        (toolbar as Toolbar).setNavigationOnClickListener{
            dismiss()
        }

        btn_retry.setOnClickListener{
            this.getCovid19Stats("South Africa")

        }
        countryBottomDialog = countryBottomDialog()
        btnFilter.setOnClickListener {
            countryBottomDialog.show()
        }
        this.adapter.setCountryClickListener(this)
        this.setErrorViewsViewsVisibility(false)
        this.getCovid19Stats("South Africa")
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


    override fun onCountryClicked(country: Country) {
        countryBottomDialog.dismiss()
        this.getCovid19Stats(country.countryName)
    }

    private fun getCovid19Stats(countryName: String){

        viewModel.observeCovid19Data(countryName)?.let {
            viewModel.observeCovid19Data(countryName)?.removeObservers(this)
            viewModel.observeCovid19Data(countryName)?.observe(viewLifecycleOwner, { res ->

                when(res.status){
                    Resources.Status.LOADING ->{
                        this.setErrorViewsViewsVisibility(false)
                        this.setShimmerLayoutVisibility(true)
                        this.setDataViewsVisibility(false)
                    }
                    Resources.Status.ERROR ->{
                        this.setErrorViewsViewsVisibility(true)
                        this.setShimmerLayoutVisibility(false)
                        this.setDataViewsVisibility(false)

                        requireActivity().resources?.let {
                            txt_message.text =  requireActivity().resources.getString(R.string.msg_error)
                        }
                    }
                    Resources.Status.SUCCESS ->{
                        this.setShimmerLayoutVisibility(false)
                        this.setErrorViewsViewsVisibility(false)
                        this.setDataViewsVisibility(true)

                        res.data?.let {
                            txt_country.text = res.data.country
                            res.data.latestStatByCountry?.let {
                                val stats : LatestStatByCountry = res.data.latestStatByCountry!![0]
                                txt_cases_confirmed.text = stats.totalCases
                                txt_cases_active.text = stats.activeCases

                                if(stats.seriousCritical == ""){

                                    txt_critical.text = "0"

                                }else{

                                    txt_critical.text = stats.seriousCritical
                                    
                                }

                                txt_deaths.text = stats.totalDeaths
                                txt_recovered.text = stats.totalRecovered
                                txt_date_msg.text = String.format("The above stats were recorded on %s",Constants.appDateFormat(stats.recordDate))
                            }

                        }

                    }

                }

            })
        }
    }

    private fun setErrorViewsViewsVisibility(isVisible: Boolean){
        if(isVisible){
            txt_message.visibility = View.VISIBLE
            btn_retry.visibility = View.VISIBLE
        }else{
            txt_message.visibility = View.GONE
            btn_retry.visibility = View.GONE
        }
    }

    private fun setShimmerLayoutVisibility(isVisible: Boolean){
        if(isVisible){
            shimmer_view_container.visibility = View.VISIBLE
            shimmer_view_container.startShimmer()
        }else{
            shimmer_view_container.visibility = View.GONE
            shimmer_view_container.stopShimmer()
        }
    }

    private fun setDataViewsVisibility(isVisible: Boolean){
        if(isVisible){
            txt_title.visibility = View.VISIBLE
            image_virus.visibility = View.VISIBLE
            txt_country.visibility = View.VISIBLE
            card_1.visibility = View.VISIBLE
            card_2.visibility = View.VISIBLE
            card_3.visibility = View.VISIBLE
            card_4.visibility = View.VISIBLE
            card_5.visibility = View.VISIBLE
            txt_date_msg.visibility = View.VISIBLE
            btnFilter.visibility = View.VISIBLE
        }else{
            txt_title.visibility = View.GONE
            image_virus.visibility = View.GONE
            txt_country.visibility = View.GONE
            card_1.visibility = View.GONE
            card_2.visibility = View.GONE
            card_3.visibility = View.GONE
            card_4.visibility = View.GONE
            card_5.visibility = View.GONE
            txt_date_msg.visibility = View.GONE
            btnFilter.visibility = View.GONE
        }
    }

    @SuppressLint("InflateParams")
    private fun countryBottomDialog() : BottomSheetDialog{
        val bottomDialog = BottomSheetDialog(requireContext())
        bottomDialog.dismissWithAnimation=true
        bottomDialog.setCancelable(true)
        bottomDialog.dismissWithAnimation = true
        val v = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_countries,null)
        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter
        bottomDialog.setContentView(v)

        return bottomDialog
    }

}