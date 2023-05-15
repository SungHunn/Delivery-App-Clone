package com.example.mvvmdeliverycloneapp.widget.adapter.listener.retaurant

import com.example.mvvmdeliverycloneapp.model.restaurant.RestaurantModel
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.RestaurantListListener

interface RestaurantLikeListListener : RestaurantListListener{

    fun onDisLikeItem(model : RestaurantModel)


    fun onLikeItem(model : RestaurantModel)
}