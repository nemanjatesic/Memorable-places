package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.fragments

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat3.nemanja_tesic_rn3017.R
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.contracts.PlaceContract
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.states.PlaceState
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.viewmodel.PlaceViewModel
import java.util.*

class ViewPlacesFragment : MyMapFragment(R.layout.fragment_map, R.id.map1) {

    private val placeViewModel: PlaceContract.ViewModel by viewModel<PlaceViewModel>()
    private var listOfOldMarkers: MutableList<Marker?> = mutableListOf()
    private var firstTime = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {
        initUi()
    }

    private fun initUi() {
        initObservers()
    }

    private fun initObservers() {
        placeViewModel.placeState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })
    }

    private fun renderState(state: PlaceState) {
        when(state) {
            is PlaceState.Success -> {
                clearMarkers()
                loadMarkers(state.places)
            }
            is PlaceState.Error -> Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            is PlaceState.Add -> Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun clearMarkers() {
        listOfOldMarkers.forEach {
            it?.remove()
        }
    }

    private fun loadMarkers(list: List<Place>) {
        if (map == null) return

        list.forEach {
            val latLng = LatLng(it.latitude, it.longitude)
            val marker = map?.addMarker(MarkerOptions().position(latLng).title(it.title).snippet(it.note))
            listOfOldMarkers.add(marker)
        }
    }

    private fun getRandomHue(): Float {
        return when (Random().nextInt(10)) {
            0 -> BitmapDescriptorFactory.HUE_YELLOW
            1 -> BitmapDescriptorFactory.HUE_VIOLET
            2 -> BitmapDescriptorFactory.HUE_AZURE
            3 -> BitmapDescriptorFactory.HUE_BLUE
            4 -> BitmapDescriptorFactory.HUE_CYAN
            5 -> BitmapDescriptorFactory.HUE_GREEN
            6 -> BitmapDescriptorFactory.HUE_MAGENTA
            7 -> BitmapDescriptorFactory.HUE_ORANGE
            8 -> BitmapDescriptorFactory.HUE_RED
            else -> BitmapDescriptorFactory.HUE_ROSE
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        placeViewModel.getAll()
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)
        if (firstTime && location != null) {
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(location.latitude, location.longitude)) // Sets the center of the map to Mountain View
                .zoom(15f) // Sets the zoom
                .build() // Creates a CameraPosition from the builder

            map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            firstTime = false
        }
    }

}