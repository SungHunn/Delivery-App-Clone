package com.example.mvvmdeliverycloneapp.data.repository.restaurant

import com.example.mvvmdeliverycloneapp.data.entity.LocationLatLngEntity
import com.example.mvvmdeliverycloneapp.data.entity.RestaurantEntity
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.RestaurantCategory

interface RestaurantRepository {

    suspend fun getList(
        restaurantCategory: RestaurantCategory,
        locationLatLngEntity: LocationLatLngEntity
    ) : List<RestaurantEntity>
}