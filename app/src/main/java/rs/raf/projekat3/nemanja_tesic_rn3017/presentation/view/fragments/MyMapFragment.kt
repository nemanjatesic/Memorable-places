package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationListener
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng

abstract class MyMapFragment(layoutId: Int, private val mapId: Int) : Fragment(layoutId), OnMapReadyCallback,
    GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
    LocationListener, GoogleMap.OnMapClickListener {

    var map: GoogleMap? = null
    var apiClient: GoogleApiClient? = null
    var locationRequest: LocationRequest? = null
    var lastKnownLocation: Location? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initMap()
    }

    private fun initMap() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkLocationPermission()
        }
        val mapFragment = childFragmentManager.findFragmentById(mapId) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap
        map?.mapType = GoogleMap.MAP_TYPE_NORMAL
        map?.uiSettings?.isZoomControlsEnabled = true
        map?.uiSettings?.isZoomGesturesEnabled = true
        map?.uiSettings?.isCompassEnabled = true
        //Initialize Google Play Services
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                buildGoogleApiClient()
                map?.isMyLocationEnabled = true
            }
        } else {
            buildGoogleApiClient()
            map?.isMyLocationEnabled = true
        }
    }

    @Synchronized
    private fun buildGoogleApiClient() {
        apiClient = GoogleApiClient.Builder(requireContext())
            .addConnectionCallbacks(this)
            .addOnConnectionFailedListener(this)
            .addApi(LocationServices.API)
            .build()
        apiClient?.connect()
    }

    override fun onConnected(p0: Bundle?) {
        locationRequest = LocationRequest()
        locationRequest?.interval = 5000
        locationRequest?.fastestInterval = 1000
        locationRequest?.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                apiClient,
                locationRequest,
                this
            )
        }
    }

    override fun onConnectionSuspended(p0: Int) {

    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }

    override fun onLocationChanged(location: Location?) {
        if (location != null) lastKnownLocation = location
    }

    override fun onMapClick(p0: LatLng?) {

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_LOCATION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (apiClient == null) {
                            buildGoogleApiClient()
                        }
                        map?.isMyLocationEnabled = true
                    }
                } else {
                    Toast.makeText(requireContext(), "permission denied", Toast.LENGTH_LONG).show()
                }
                return
            }
        }
    }

    protected fun checkLocationPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            } else {
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION
                )
            }
            false
        } else {
            true
        }
    }

    companion object {
        const val MY_PERMISSIONS_REQUEST_LOCATION = 99
    }
}