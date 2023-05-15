package com.example.mvvmdeliverycloneapp.widget.adapter.listener.order

import com.example.mvvmdeliverycloneapp.widget.adapter.listener.AdapterListener

interface OrderListListener : AdapterListener {

    fun writeRestaurantReview(orderId : String , restaurantTitle : String)
}