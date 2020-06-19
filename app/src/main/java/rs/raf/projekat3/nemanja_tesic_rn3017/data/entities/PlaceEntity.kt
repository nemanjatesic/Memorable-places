package rs.raf.projekat3.nemanja_tesic_rn3017.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "places")
data class PlaceEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val note: String,
    val dateCreated: String
)