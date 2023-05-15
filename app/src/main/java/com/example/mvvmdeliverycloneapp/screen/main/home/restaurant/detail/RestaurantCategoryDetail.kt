package com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.detail

import androidx.annotation.StringRes
import com.example.mvvmdeliverycloneapp.R

enum class RestaurantCategoryDetail (
    @StringRes val categoryNameId : Int
){
    MENU(R.string.menu) , REVIEW(R.string.review)
}