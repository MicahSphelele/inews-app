package sphe.inews.ui.main.weather

import android.Manifest
import android.content.pm.PackageManager
import android.location.Address
import android.os.Bundle
import android.os.Looper.getMainLooper
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.*
import com.google.android.material.transition.MaterialElevationScale
import sphe.inews.R
import sphe.inews.databinding.FragmentWeatherBinding
import sphe.inews.util.AppLogger
import sphe.inews.util.LocationUtils


class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var binding: FragmentWeatherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        exitTransition = MaterialElevationScale(/* growing= */ false)
        enterTransition = MaterialElevationScale(/* growing= */ true)
        locationCallback = object : LocationCallback() {

            override fun onLocationResult(result: LocationResult) {
                super.onLocationResult(result)
                AppLogger.error("onLocationResult: ${result.lastLocation.latitude},${result.lastLocation.longitude}")
                val address = LocationUtils.getLocationAddress(
                    context = requireContext(),
                    latitude = -29.7290961,
                    longitude = 31.0698632
                )
                binding.text.text = String.format("%s # %s #" +
                        " %s # %s # " +
                        " %s # %s," +
                        " %s # %s," +
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
                    address.subThoroughfare)


                fusedLocationProviderClient.removeLocationUpdates(locationCallback)
                //ZA # KwaZulu-Natal # South Africa # 1, en_ZA # 1 Nokwe Ave, Umhlanga Ridge, Umhlanga, 4319, South Africa, Umhlanga
            // # Umhlanga Ridge,null # null # 4319
            }

            override fun onLocationAvailability(availability: LocationAvailability) {
                super.onLocationAvailability(availability)
                AppLogger.error("onLocationAvailability: ${availability.isLocationAvailable}")
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentWeatherBinding.bind(view)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        requestLocationUpdate()
    }

    private fun requestLocationUpdate() {
        val locationRequest = LocationUtils.getLocationRequest()

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

        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            getMainLooper()
        )
    }

}