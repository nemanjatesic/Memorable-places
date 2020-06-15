package rs.raf.projekat3.nemanja_tesic_rn3017.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "beleske")
data class SomethingEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long
)