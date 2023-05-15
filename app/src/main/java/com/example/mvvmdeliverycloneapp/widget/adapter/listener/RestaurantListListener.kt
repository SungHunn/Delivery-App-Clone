package com.example.mvvmdeliverycloneapp.widget.adapter.listener

import com.example.mvvmdeliverycloneapp.model.restaurant.RestaurantModel

interface RestaurantListListener : AdapterListener{

    fun onClickItem(model : RestaurantModel)
}