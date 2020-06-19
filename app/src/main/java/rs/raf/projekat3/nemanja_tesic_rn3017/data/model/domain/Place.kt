package rs.raf.projekat3.nemanja_tesic_rn3017.data.model.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Place (
    val id: Long,
    val latitude: Double,
    val longitude: Double,
    val title: String,
    val note: String,
    val dateCreated: String
): Parcelable