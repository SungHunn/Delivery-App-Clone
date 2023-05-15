package com.example.mvvmdeliverycloneapp.data.network

import com.example.mvvmdeliverycloneapp.data.response.restaurant.RestaurantFoodResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface FoodApiService {

    @GET("restaurants/{restaurantId}/foods")
    suspend fun getRestaurantFoods(
        @Path("restaurantId") restaurantId : Long
    ) :Response<List<RestaurantFoodResponse>>
}