package com.example.mvvmdeliverycloneapp.model.restaurant.food

import com.example.mvvmdeliverycloneapp.data.entity.RestaurantFoodEntity
import com.example.mvvmdeliverycloneapp.model.CellType
import com.example.mvvmdeliverycloneapp.model.Model


data class FoodModel(
    override val id: Long,
    override val type: CellType = CellType.FOOD_CELL,
    val title: String,
    val description: String,
    val price: Int,
    val imageUrl: String,
    val restaurantId: Long,
    val foodId: String,
    val restaurantTitle: String
) : Model(id, type) {

    fun toEntity(basketIndex: Int) = RestaurantFoodEntity(
        "${foodId}_${basketIndex}", title, description, price, imageUrl, restaurantId, restaurantTitle
    )

}
