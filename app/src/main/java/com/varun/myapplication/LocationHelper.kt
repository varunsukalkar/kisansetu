package com.varun.myapplication


import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.provider.Settings

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Task
import java.util.*

class LocationHelper(private val context: Context) {

    private val fusedLocationProviderClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)

    fun getAddressString(onAddressFetched: (String?) -> Unit) {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                (context as Activity),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION),
                101
            )
            onAddressFetched(null)
            return
        }

        if (!isLocationEnabled()) {
            AlertDialog.Builder(context)
                .setTitle("Enable GPS Service")
                .setMessage("We need your GPS location to fetch your address.")
                .setCancelable(false)
                .setPositiveButton("Enable") { _, _ ->
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }
                .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                .show()
            onAddressFetched(null)
            return
        }

        fusedLocationProviderClient.lastLocation.addOnCompleteListener { task: Task<Location> ->
            val location: Location? = task.result
            if (location != null) {
                try {
                    val geocoder = Geocoder(context, Locale.getDefault())
                    val addresses: MutableList<Address>? =
                        geocoder.getFromLocation(location.latitude, location.longitude, 1)
                    if (addresses != null) {
                        if (addresses.isNotEmpty()) {
                            val address = addresses[0]
                            val addressString = buildString {
                                append(address.getAddressLine(0))
                            }
                            onAddressFetched(addressString)
                        } else {
                            onAddressFetched("No address found")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    onAddressFetched(null)
                }
            } else {
                onAddressFetched("Location is null")
            }
        }
    }

    private fun isLocationEnabled(): Boolean {
        val locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
    }
}
