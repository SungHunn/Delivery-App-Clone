package com.example.mvvmdeliverycloneapp.screen.mylocation

import androidx.annotation.StringRes
import com.example.mvvmdeliverycloneapp.data.entity.MapSearchInfoEntity
import com.example.mvvmdeliverycloneapp.screen.main.home.HomeState


sealed class MyLocationState {

    object Uninitialized: MyLocationState()

    object Loading: MyLocationState()

    data class Success(
        val mapSearchInfoEntity: MapSearchInfoEntity
    ): MyLocationState()

    data class Confirm(
        val mapSearchInfoEntity: MapSearchInfoEntity
    ): MyLocationState()

    data class Error(
        @StringRes val messageId : Int
    ): MyLocationState()
}