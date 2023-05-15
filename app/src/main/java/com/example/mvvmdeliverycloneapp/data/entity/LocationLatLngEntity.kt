package com.example.mvvmdeliverycloneapp.data.entity

import android.os.Parcelable
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import androidx.room.Entity

@Parcelize
@Entity
data class LocationLatLngEntity(
    val latitude: Double,
    val longitude: Double,
    @PrimaryKey(autoGenerate = true)
    val id: Long = -1,
): Parcelable {

    override fun equals(other: Any?): Boolean {
        return if (other is LocationLatLngEntity) {
            (latitude == other.latitude
                    && longitude == other.longitude)
        } else {
            this === other
        }
    }

}