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
import sphe.inews.R
import sphe.inews.databinding.FragmentCovid19StatsBinding
import sphe.inews.models.Country
import sphe.inews.models.covid.LatestStatByCountry
import sphe.inews.network.Resources
import sphe.inews.ui.main.adapters.CountryAdapter
import sphe.inews.util.Constants
import javax.inject.Inject

@AndroidEntryPoint
class CovidStatDialogFragment : DialogFragment(R.layout.fragment_covid19_stats), CountryAdapter.CountryListener {

    @Inject
    lateinit var adapter: CountryAdapter

    private lateinit var countryBottomDialog: BottomSheetDialog

    private val viewModel by viewModels<Covid19StatsViewModel>()

    private lateinit var binding: FragmentCovid19StatsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.FullScreenDialogStyle)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCovid19StatsBinding.bind(view)

        (binding.toolbar as Toolbar).navigationIcon =
            ContextCompat.getDrawable(requireContext(), R.drawable.ic_action_home)

        (binding.toolbar as Toolbar).setNavigationOnClickListener {
            dismiss()
        }

        binding.btnRetry.setOnClickListener {
            this.getCovid19Stats("South Africa")

        }
        countryBottomDialog = countryBottomDialog()
        binding.btnFilter.setOnClickListener {
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

    private fun getCovid19Stats(countryName: String) {

        viewModel.observeCovid19Data(countryName)?.let {
            viewModel.observeCovid19Data(countryName)?.removeObservers(this)
            viewModel.observeCovid19Data(countryName)?.observe(viewLifecycleOwner, { res ->

                when (res.status) {
                    Resources.Status.LOADING -> {
                        this.setErrorViewsViewsVisibility(false)
                        this.setShimmerLayoutVisibility(true)
                        this.setDataViewsVisibility(false)
                    }
                    Resources.Status.ERROR -> {
                        this.setErrorViewsViewsVisibility(true)
                        this.setShimmerLayoutVisibility(false)
                        this.setDataViewsVisibility(false)

                        requireActivity().resources?.let {
                            binding.txtMessage.text =
                                requireActivity().resources.getString(R.string.msg_error)
                        }
                    }
                    Resources.Status.SUCCESS -> {
                        this.setShimmerLayoutVisibility(false)
                        this.setErrorViewsViewsVisibility(false)
                        this.setDataViewsVisibility(true)

                        res.data?.let {
                            binding.txtCountry.text = res.data.country
                            res.data.latestStatByCountry?.let {
                                val stats: LatestStatByCountry = res.data.latestStatByCountry!![0]
                                binding.txtCasesConfirmed.text = stats.totalCases
                                binding.txtCasesActive.text = stats.activeCases

                                if (stats.seriousCritical == "") {

                                    binding.txtCritical.text = "0"

                                } else {

                                    binding.txtCritical.text = stats.seriousCritical

                                }

                                binding.txtDeaths.text = stats.totalDeaths
                                binding.txtRecovered.text = stats.totalRecovered
                                binding.txtDateMsg.text = String.format(
                                    "The above stats were recorded on %s",
                                    Constants.appDateFormat(stats.recordDate)
                                )
                            }

                        }

                    }

                }

            })
        }
    }

    private fun setErrorViewsViewsVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.txtMessage.visibility = View.VISIBLE
            binding.btnRetry.visibility = View.VISIBLE
        } else {
            binding.txtMessage.visibility = View.GONE
            binding.btnRetry.visibility = View.GONE
        }
    }

    private fun setShimmerLayoutVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.shimmerViewContainer.visibility = View.VISIBLE
            binding.shimmerViewContainer.startShimmer()
        } else {
            binding.shimmerViewContainer.visibility = View.GONE
            binding.shimmerViewContainer.stopShimmer()
        }
    }

    private fun setDataViewsVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.txtTitle.visibility = View.VISIBLE
            binding.imageVirus.visibility = View.VISIBLE
            binding.txtCountry.visibility = View.VISIBLE
            binding.card1.visibility = View.VISIBLE
            binding.card2.visibility = View.VISIBLE
            binding.card3.visibility = View.VISIBLE
            binding.card4.visibility = View.VISIBLE
            binding.card5.visibility = View.VISIBLE
            binding.txtDateMsg.visibility = View.VISIBLE
            binding.btnFilter.visibility = View.VISIBLE
        } else {
            binding.txtTitle.visibility = View.GONE
            binding.imageVirus.visibility = View.GONE
            binding.txtCountry.visibility = View.GONE
            binding.card1.visibility = View.GONE
            binding.card2.visibility = View.GONE
            binding.card3.visibility = View.GONE
            binding.card4.visibility = View.GONE
            binding.card5.visibility = View.GONE
            binding.txtDateMsg.visibility = View.GONE
            binding.btnFilter.visibility = View.GONE
        }
    }

    @SuppressLint("InflateParams")
    private fun countryBottomDialog(): BottomSheetDialog {
        val bottomDialog = BottomSheetDialog(requireContext())
        bottomDialog.dismissWithAnimation = true
        bottomDialog.setCancelable(true)
        bottomDialog.dismissWithAnimation = true
        val v = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_countries, null)
        val recyclerView = v.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(context, 3)
        recyclerView.adapter = adapter
        bottomDialog.setContentView(v)

        return bottomDialog
    }

}