package rs.raf.projekat3.nemanja_tesic_rn3017.data.repositories

import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat3.nemanja_tesic_rn3017.data.datasources.local.PlaceDao
import rs.raf.projekat3.nemanja_tesic_rn3017.data.entities.PlaceEntity
import rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain.Place

class PlaceRepositoryImpl(private val placeDao: PlaceDao): PlaceRepository {

    override fun insert(place: Place): Completable {
        val placeEntity =
            PlaceEntity(
                place.id,
                place.latitude,
                place.longitude,
                place.title,
                place.note,
                place.dateCreated
            )
        return placeDao.insert(placeEntity)
    }

    override fun update(place: Place): Completable {
        val placeEntity =
            PlaceEntity(
                place.id,
                place.latitude,
                place.longitude,
                place.title,
                place.note,
                place.dateCreated
            )
        return placeDao.update(placeEntity)
    }

    override fun delete(id: Long): Completable {
        return placeDao.delete(id)
    }

    override fun getAll(): Observable<List<Place>> {
        return placeDao
            .getAll()
            .map {
                it.map {place ->
                    Place(
                        place.id,
                        place.latitude,
                        place.longitude,
                        place.title,
                        place.note,
                        place.dateCreated
                    )
                }
            }
    }

    override fun filter(text: String, ascending: Boolean): Observable<List<Place>> {
        val list = if (ascending) placeDao.filterAsc(text) else placeDao.filterDesc(text)
        return list
            .map {
                it.map {place ->
                    Place(
                        place.id,
                        place.latitude,
                        place.longitude,
                        place.title,
                        place.note,
                        place.dateCreated
                    )
                }
            }
    }

}