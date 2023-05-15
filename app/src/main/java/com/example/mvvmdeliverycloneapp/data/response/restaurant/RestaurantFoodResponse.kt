package com.example.mvvmdeliverycloneapp.data.response.restaurant

import com.example.mvvmdeliverycloneapp.data.entity.RestaurantFoodEntity

data class RestaurantFoodResponse(
    val id : String,
    val title : String,
    val description : String,
    val price : Int,
    val imageUrl : String,
) {
    fun toEntity(restaurantId : Long, restaurantTitle: String) = RestaurantFoodEntity(
        id,
        title,
        description,
        price.toDouble().toInt(),
        imageUrl,
        restaurantId,
        restaurantTitle
    )
}
