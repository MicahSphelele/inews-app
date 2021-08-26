package sphe.inews.util

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.*
import java.util.*

object LocationUtils {

    fun isGPSOpen(context: Context): Boolean {
        val manager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return manager.isProviderEnabled(LocationManager.GPS_PROVIDER) || manager.isProviderEnabled(
            LocationManager.NETWORK_PROVIDER
        )
    }

    fun isLocationPermissionEnabled(context: Context): Boolean {
        return (ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED);
    }

    fun requestLocationPermission(activity: Activity) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            Constants.LOCATION_REQUEST_CODE
        )
    }

    fun getLocationAddress(context: Context, latitude: Double, longitude: Double): Address {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addressList = geocoder.getFromLocation(latitude, longitude, 1) as ArrayList<Address>
        return addressList[0]
    }

    fun locationCallback(context: Context) : LocationCallback {

        val client = LocationServices.getFusedLocationProviderClient(context)
/*      LocationRequest locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        client.requestLocationUpdates(locationRequest, locationCallback, getMainLooper());
        */
        return object : LocationCallback() {
            override fun onLocationAvailability(availability: LocationAvailability) {
                super.onLocationAvailability(availability)
            }

            override fun onLocationResult(p0: LocationResult) {
                super.onLocationResult(p0)
            }
        }

    }
}