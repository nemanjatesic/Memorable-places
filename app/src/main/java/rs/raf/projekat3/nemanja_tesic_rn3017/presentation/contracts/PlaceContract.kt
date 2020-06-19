package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.contracts

import androidx.lifecycle.LiveData
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place
import rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.states.PlaceState

interface PlaceContract {

    interface ViewModel {
        val placeState: LiveData<PlaceState>

        fun insert(place: Place)
        fun update(place: Place)
        fun delete(id: Long)
        fun getAll()
        fun filter(text: String, ascending: Boolean)
    }
}
