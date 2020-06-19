package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.activities

import android.os.Bundle
import android.widget.Toast
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_edit.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import rs.raf.projekat3.nemanja_tesic_rn3017.R
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.contracts.PlaceContract
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.fragments.ListFragment
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.viewmodel.PlaceViewModel

class EditPlaceActivity : MyMapActivity(R.layout.activity_edit, R.id.map3) {

    private val placeViewModel: PlaceContract.ViewModel by viewModel<PlaceViewModel>()
    private lateinit var place: Place

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    private fun init() {
        place = intent.getParcelableExtra(ListFragment.PLACE)?: Place(-1,0.0,0.0,"","","")
        if (place.id == (-1).toLong()) {
            Toast.makeText(this, "Error occurred", Toast.LENGTH_SHORT).show()
            finish()
        }
        placeViewModel.getAll()
        titleEditEt.setText(place.title)
        noteEditEt.setText(place.note)
        initListeners()
    }

    private fun initListeners() {
        cancelEditBtn.setOnClickListener {
            finish()
        }
        saveBtn.setOnClickListener {
            placeViewModel.update(
                Place(
                    place.id,
                    place.latitude,
                    place.longitude,
                    titleEditEt.text.toString(),
                    noteEditEt.text.toString(),
                    place.dateCreated)
            )
            finish()
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        super.onMapReady(googleMap)
        if (checkLocationPermission()) {
            loadMarker()
            zoomOnMarker()
        }
    }

    private fun loadMarker() {
        val latLng = LatLng(place.latitude, place.longitude)
        map?.addMarker(MarkerOptions().position(latLng).title(place.title).snippet(place.note))
    }

    private fun zoomOnMarker() {
        val cameraPosition = CameraPosition.Builder()
            .target(LatLng(place.latitude, place.longitude)) // Sets the center of the map to Mountain View
            .zoom(15f) // Sets the zoom
            .build() // Creates a CameraPosition from the builder

        map?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
    }
}