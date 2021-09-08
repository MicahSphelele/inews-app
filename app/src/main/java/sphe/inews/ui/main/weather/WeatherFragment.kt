package sphe.inews.ui.main.weather

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.transition.MaterialElevationScale
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.FragmentWeatherBinding
import sphe.inews.util.AppLogger
import sphe.inews.util.LocationUtils

@AndroidEntryPoint
class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var binding: FragmentWeatherBinding

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
        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            val latitude = it.latitude
            val longitude = it.longitude

            AppLogger.error("onLocationResult: $latitude,$longitude}")

            val address = LocationUtils.getLocationAddress(
                context = requireContext(),
                latitude =  latitude,
                longitude = longitude
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
        }.addOnFailureListener {
            AppLogger.info("No")
        }
        
    }

}