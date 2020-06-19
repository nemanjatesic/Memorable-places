package rs.raf.projekat3.nemanja_tesic_rn3017.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place

interface PlaceRepository {
    fun insert(place: Place): Completable

    fun update(place: Place): Completable

    fun delete(id: Long): Completable

    fun getAll(): Observable<List<Place>>

    fun filter(text: String, ascending: Boolean): Observable<List<Place>>
}