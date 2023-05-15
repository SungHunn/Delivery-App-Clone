package com.example.mvvmdeliverycloneapp.data.entity

import android.net.Uri

data class RestaurantReviewEntity(
    override val id: Long,
    val title : String,
    val description : String,
    val grade : Int,
    val images : List<Uri>? = null
) : Entity
