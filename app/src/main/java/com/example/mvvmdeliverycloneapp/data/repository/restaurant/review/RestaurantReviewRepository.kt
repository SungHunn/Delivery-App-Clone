package com.example.mvvmdeliverycloneapp.data.repository.restaurant.review

import com.example.mvvmdeliverycloneapp.data.entity.RestaurantReviewEntity

interface RestaurantReviewRepository {

   suspend fun getReviews(restaurantTitle : String) : DefaultRestaurantReviewRepository.Result
}