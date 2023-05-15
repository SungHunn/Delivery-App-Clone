package com.example.mvvmdeliverycloneapp.screen.main.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmdeliverycloneapp.R
import com.example.mvvmdeliverycloneapp.data.entity.LocationLatLngEntity
import com.example.mvvmdeliverycloneapp.data.entity.MapSearchInfoEntity
import com.example.mvvmdeliverycloneapp.data.entity.RestaurantFoodEntity
import com.example.mvvmdeliverycloneapp.data.repository.map.MapRepository
import com.example.mvvmdeliverycloneapp.data.repository.restaurant.food.RestaurantFoodRepository
import com.example.mvvmdeliverycloneapp.data.repository.user.DefaultUserRepository
import com.example.mvvmdeliverycloneapp.data.repository.user.UserRepository
import com.example.mvvmdeliverycloneapp.screen.base.BaseViewModel
import com.google.firebase.firestore.auth.User
import kotlinx.coroutines.launch

class HomeViewModel(
    private val mapRepository: MapRepository,
    private val userRepository: UserRepository,
    private val restaurantFoodRepository: RestaurantFoodRepository
) : BaseViewModel() {

    companion object {
        const val MY_LOCATION_KEY = "MyLocation"
    }

    val homeStateLiveData = MutableLiveData<HomeState>(HomeState.Uninitialized)

    val foodMenuBasketLiveData = MutableLiveData<List<RestaurantFoodEntity>>()

    fun loadReverseGeoInformation(
        locationLatLngEntity: LocationLatLngEntity
    ) = viewModelScope.launch {
        homeStateLiveData.value = HomeState.Loading
        val userLocation = userRepository.getUserLocation()
        val currentLocation = userLocation ?: locationLatLngEntity

        val addressInfo = mapRepository.getReverseGeoInformation(currentLocation)
        addressInfo?.let { info ->
            homeStateLiveData.value = HomeState.Success(
                mapSearchInfoEntity = info.toSearchInfoEntity(locationLatLngEntity),
                isLocationSame = currentLocation == locationLatLngEntity
            )

        } ?: kotlin.run {
            homeStateLiveData.value = HomeState.Error(
                R.string.can_not_load_address_info
            )
        }
    }

    fun getMapSearchInfo(): MapSearchInfoEntity? {
        when (val data = homeStateLiveData.value) {
            is HomeState.Success -> {
                return data.mapSearchInfoEntity
            }
        }
        return null
    }

    fun checkMyBasket() = viewModelScope.launch {
        foodMenuBasketLiveData.value = restaurantFoodRepository.getAllFoodMenuListInBasket()
    }


}