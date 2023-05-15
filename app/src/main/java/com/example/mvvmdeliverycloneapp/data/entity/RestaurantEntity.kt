package com.example.mvvmdeliverycloneapp.data.entity

import android.os.Parcelable
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.RestaurantCategory
import com.example.mvvmdeliverycloneapp.util.convertor.RoomTypeConverters
import kotlinx.parcelize.Parcelize


@Parcelize  //파싱
@androidx.room.Entity
@TypeConverters(RoomTypeConverters::class)
data class RestaurantEntity(
    override val id: Long,
    val restaurantInfoId : Long,
    val restaurantCategory : RestaurantCategory,
    @PrimaryKey val restaurantTitle : String,
    val restaurantImageUrl : String,
    val grade : Float,
    val reviewCount : Int,
    val deliveryTimeRange : Pair<Int, Int>,
    val deliveryTipRange : Pair<Int , Int>,
    val restaurantTelNumber : String?

) : Entity, Parcelable
