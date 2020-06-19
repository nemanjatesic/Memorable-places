package rs.raf.projekat3.nemanja_tesic_rn3017.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.projekat3.nemanja_tesic_rn3017.data.datasources.local.PlaceDao
import rs.raf.projekat3.nemanja_tesic_rn3017.data.entities.PlaceEntity

@Database(
    entities = [PlaceEntity::class],
    version = 4,
    exportSchema = false)
abstract class ProjectDatabase : RoomDatabase()  {
    abstract fun getPlaceDao(): PlaceDao
}