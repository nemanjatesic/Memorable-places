package rs.raf.projekat3.nemanja_tesic_rn3017.data.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import io.reactivex.Completable
import io.reactivex.Observable
import rs.raf.projekat3.nemanja_tesic_rn3017.data.entities.PlaceEntity

@Dao
abstract class PlaceDao {
    @Insert
    abstract fun insert(place: PlaceEntity): Completable

    @Update
    abstract fun update(place: PlaceEntity): Completable

    @Query("DELETE FROM places WHERE id = :id")
    abstract fun delete(id: Long): Completable

    @Query("SELECT * FROM places")
    abstract fun getAll(): Observable<List<PlaceEntity>>

    @Query("SELECT * FROM places WHERE (title LIKE '%' || :text || '%' OR note LIKE '%' || :text || '%') ORDER BY dateCreated DESC")
    abstract fun filterDesc(text: String): Observable<List<PlaceEntity>>

    @Query("SELECT * FROM places WHERE (title LIKE '%' || :text || '%' OR note LIKE '%' || :text || '%') ORDER BY dateCreated ASC")
    abstract fun filterAsc(text: String): Observable<List<PlaceEntity>>
}