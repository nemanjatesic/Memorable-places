package rs.raf.projekat3.nemanja_tesic_rn3017.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rs.raf.projekat3.nemanja_tesic_rn3017.data.entities.SomethingEntity

@Database(
    entities = [SomethingEntity::class],
    version = 1,
    exportSchema = false)
abstract class ProjectDatabase : RoomDatabase()  {

}