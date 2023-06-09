package com.example.mvvmdeliverycloneapp.screen.order

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mvvmdeliverycloneapp.R
import com.example.mvvmdeliverycloneapp.data.repository.order.DefaultOrderRepository
import com.example.mvvmdeliverycloneapp.data.repository.order.OrderRepository
import com.example.mvvmdeliverycloneapp.data.repository.restaurant.food.RestaurantFoodRepository
import com.example.mvvmdeliverycloneapp.model.CellType
import com.example.mvvmdeliverycloneapp.model.restaurant.food.FoodModel
import com.example.mvvmdeliverycloneapp.screen.base.BaseViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class OrderMenuListViewModel(
    private val restaurantFoodRepository: RestaurantFoodRepository,
    private val orderRepository: OrderRepository,
    private val firebaseAuth: FirebaseAuth
) : BaseViewModel() {

    val orderMenuState = MutableLiveData<OrderMenuState>(OrderMenuState.Uninitialized)

    override fun fetchData(): Job = viewModelScope.launch {
        orderMenuState.value = OrderMenuState.Loading
        val foodMenuList = restaurantFoodRepository.getAllFoodMenuListInBasket()
        orderMenuState.value = OrderMenuState.Success(
            foodMenuList.map {
                FoodModel(
                    id = it.hashCode().toLong(),
                    type = CellType.ORDER_FOOD_CELL,
                    title = it.title,
                    description = it.description,
                    price = it.price,
                    imageUrl = it.imageUrl,
                    restaurantId = it.restaurantId,
                    foodId = it.id,
                    restaurantTitle = it.restaurantTitle
                )
            }
        )
    }

    fun removeOrderMenu(foodModel: FoodModel) = viewModelScope.launch {
        restaurantFoodRepository.removeFoodMenuListInBasket(foodModel.foodId)
        fetchData()
    }

    fun clearOrderMenu() = viewModelScope.launch {
        restaurantFoodRepository.clearFoodMenuListInBasket()
        fetchData()
    }

    fun orderMenu() = viewModelScope.launch {
        val foodMenuList = restaurantFoodRepository.getAllFoodMenuListInBasket()
        if (foodMenuList.isNotEmpty()) {
            val restaurantId = foodMenuList.first().restaurantId
            val restaurantTitle = foodMenuList.first().restaurantTitle
            firebaseAuth.currentUser?.let { user ->
                when (val data = orderRepository.orderMenu(user.uid, restaurantId, foodMenuList, restaurantTitle)) {
                    is DefaultOrderRepository.Result.Success<*> -> {
                        restaurantFoodRepository.clearFoodMenuListInBasket()
                        orderMenuState.value = OrderMenuState.Order
                    }
                    is DefaultOrderRepository.Result.Error -> {
                        orderMenuState.value = OrderMenuState.Error(R.string.request_error, data.e)
                    }
                }
            } ?: kotlin.run {
                orderMenuState.value = OrderMenuState.Error(R.string.user_id_not_found, IllegalAccessException())
            }
        }
    }

}