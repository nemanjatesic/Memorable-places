package rs.raf.projekat3.nemanja_tesic_rn3017.presentation.view.states

import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place

sealed class PlaceState {
    data class Success(val places: List<Place>): PlaceState()
    data class Error(val message: String): PlaceState()
    data class Add(val message: String): PlaceState()
    data class Edit(val message: String): PlaceState()
    data class Delete(val message: String): PlaceState()
}