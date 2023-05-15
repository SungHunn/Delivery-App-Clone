package com.example.mvvmdeliverycloneapp.widget.adapter.listener.order

import com.example.mvvmdeliverycloneapp.model.restaurant.food.FoodModel
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.AdapterListener

interface OrderMenuListListener : AdapterListener {

    fun onRemoveItem(foodModel: FoodModel){

    }
}