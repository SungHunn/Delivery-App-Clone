package com.example.mvvmdeliverycloneapp.model.order

import com.example.mvvmdeliverycloneapp.data.entity.OrderEntity
import com.example.mvvmdeliverycloneapp.data.entity.RestaurantFoodEntity
import com.example.mvvmdeliverycloneapp.model.CellType
import com.example.mvvmdeliverycloneapp.model.Model
import java.util.logging.LogManager

data class OrderModel(
    override val id: Long,
    override val type: CellType = CellType.ORDER_CELL,
    val orderId : String,
    val userId : String,
    val restaurantId : Long,
    val foodMenuList : List<RestaurantFoodEntity>,
    val restaurantTitle : String
) : Model(id , type) {

    fun toEntity() = OrderEntity(
        id = orderId,
        userId = userId,
        restaurantId = restaurantId,
        foodMenuList = foodMenuList,
        restaurantTitle = restaurantTitle
    )
}
