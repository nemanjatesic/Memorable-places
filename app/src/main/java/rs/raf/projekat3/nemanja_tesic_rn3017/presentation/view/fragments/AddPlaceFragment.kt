package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.fragments

import android.location.Location
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.fragment_add.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat3.nemanja_tesic_rn3017.R
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.contracts.PlaceContract
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.states.PlaceState
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.viewmodel.PlaceViewModel
import java.text.SimpleDateFormat
import java.util.*


class AddPlaceFragment : MyMapFragment(R.layout.fragment_add, R.id.map) {

    private val placeViewModel: PlaceContract.ViewModel by viewModel<PlaceViewModel>()
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
        initListeners()
    }

    private fun initObservers() {
        placeViewModel.placeState.observe(viewLifecycleOwner, Observer {
            renderState(it)
        })
    }

    private fun initListeners() {
        cancelBtn.setOnClickListener {
            placeViewModel.getAll()
        }

        addBtn.setOnClickListener {
            if (lastKnownLocation == null) {
                Toast.makeText(context, "Error occurred please try again", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (titleEt.text.toString().isEmpty() || noteEt.text.toString().isEmpty()) {
                Toast.makeText(context, "You must enter both title and note", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val sdf = SimpleDateFormat("yyyy.MM.dd. HH:mm")

            placeViewModel.insert(Place(0, lastKnownLocation!!.latitude, lastKnownLocation!!.longitude,
                titleEt.text.toString(), noteEt.text.toString(), sdf.format(Date())))

            titleEt.setText("")
            noteEt.setText("")
        }
    }

    private fun renderState(state: PlaceState) {
        when(state) {
            is PlaceState.Success -> loadMarkers(state.places)
            is PlaceState.Error -> Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
            is PlaceState.Add -> Toast.makeText(context, state.message, Toast.LENGTH_SHORT).show()
        }
    }

    private fun loadMarkers(list: List<Place>) {
        if (map == null) return

        list.forEach {
            val latLng = LatLng(it.latitude, it.longitude)
            map?.addMarker(MarkerOptions().position(latLng).title(it.title).snippet(it.note))
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        if (checkLocationPermission()) {
            placeViewModel.getAll()
        }
    }

    override fun onLocationChanged(location: Location?) {
        super.onLocationChanged(location)
        if (firstTime && location != null) {
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(location.latitude, location.longitude))
                .zoom(15f) // Sets the zoom
                .build() // Creates a CameraPosition from the builder

            map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            firstTime = false
        }
    }

}