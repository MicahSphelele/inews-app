package sphe.inews.presentation.ui.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.FragmentWeatherBinding
import sphe.inews.domain.NetworkResult
import sphe.inews.util.AppLogger
import sphe.inews.util.Constants
import sphe.inews.util.LocationUtils

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentWeatherBinding
    private val viewModel by viewModels<WeatherViewModel>()
    private var locationCallback: LocationCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(/* growing= */ false)
        enterTransition = MaterialElevationScale(/* growing= */ true)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWeatherBinding.bind(view)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                locationResult ?: return

                val latitude = locationResult.locations[0].latitude
                val longitude = locationResult.locations[0].longitude


                val address = LocationUtils.getLocationAddress(
                    context = requireContext(),
                    latitude = latitude,
                    longitude = longitude
                )

                val data = String.format(
                    "%s # %s #" +
                            " %s # %s # " +
                            " %s # %s #" +
                            " %s # %s #" +
                            " %s # %s # %s # %s",

                    address.countryCode,
                    address.adminArea,
                    address.countryName,
                    address.featureName,
                    address.locale,
                    address.getAddressLine(0),
                    address.locality,
                    address.subLocality,
                    address.premises,
                    address.phone,
                    address.postalCode,
                    address.subThoroughfare
                )

                AppLogger.info("onLocationResult: $data")

                viewModel
                    .getWeatherForecast(
                        String.format("%s,%s", address.locality, address.adminArea),
                        5
                    )
                    .observe(viewLifecycleOwner) { weather ->
                        when (weather) {
                            is NetworkResult.Loading -> {
                                AppLogger.info("Loading")
                                binding.progress.visibility = View.VISIBLE
                            }
                            is NetworkResult.Error -> {
                                AppLogger.info("Error")
                                binding.progress.visibility = View.GONE
                                Toast.makeText(
                                    requireContext(),
                                    "Error: ${weather.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                            is NetworkResult.Success -> {
                                AppLogger.info("Success")
                                binding.progress.visibility = View.GONE
                                val response = weather.data

                                binding.weatherCondition = response?.current?.condition?.text
                                binding.lastUpdated = String.format(
                                    "Last Updated: %s",
                                    response?.current?.lastUpdated
                                )
                                binding.place = String.format(
                                    "%s - %s",
                                    response?.location?.name,
                                    response?.location?.region
                                )
                                binding.weatherInDegree = String.format(
                                    "%s %s",
                                    response?.current?.tempInCelsius.toString(),
                                    Constants.CELSIUS
                                )

                            }
                        }
                    }
                locationCallback?.let {
                    fusedLocationProviderClient.removeLocationUpdates(it)
                }

            }
        }

        locationCallback?.let {
            fusedLocationProviderClient.requestLocationUpdates(
                LocationUtils.getLocationRequest(),
                it,
                Looper.getMainLooper()
            )
        }
    }
}