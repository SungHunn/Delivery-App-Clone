package com.example.mvvmdeliverycloneapp.widget.adapter.viewholder.order

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.mvvmdeliverycloneapp.R
import com.example.mvvmdeliverycloneapp.databinding.ViewholderFoodMenuBinding
import com.example.mvvmdeliverycloneapp.databinding.ViewholderOrderBinding
import com.example.mvvmdeliverycloneapp.databinding.ViewholderOrderMenuBinding
import com.example.mvvmdeliverycloneapp.extensions.clear
import com.example.mvvmdeliverycloneapp.extensions.load
import com.example.mvvmdeliverycloneapp.model.order.OrderModel
import com.example.mvvmdeliverycloneapp.model.restaurant.food.FoodModel
import com.example.mvvmdeliverycloneapp.screen.base.BaseViewModel
import com.example.mvvmdeliverycloneapp.util.provider.ResourcesProvider
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.AdapterListener
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.order.OrderListListener
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.order.OrderMenuListListener
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.retaurant.FoodMenuListListener
import com.example.mvvmdeliverycloneapp.widget.adapter.viewholder.ModelViewHolder

class OrderViewHolder(
    private val binding : ViewholderOrderBinding,
    viewModel : BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<OrderModel>(binding, viewModel, resourcesProvider){

    override fun reset() = Unit


    override fun bindViews(model: OrderModel, adapterListener: AdapterListener) {
        if(adapterListener is OrderListListener){
            binding.root.setOnClickListener {
                adapterListener.writeRestaurantReview(model.orderId, model.restaurantTitle)
            }
        }
    }


    override fun bindData(model: OrderModel) {
        super.bindData(model)
        with(binding){
            orderTitleText.text = resourcesProvider.getString(R.string.order_history_title, model.orderId)

            val foodMenuList = model.foodMenuList

            foodMenuList
                .groupBy { it.title }
                .entries.forEach { (title, menuList) ->
                    val orderDataStr =
                        orderContentText.text.toString() + "메뉴 : $title | 가격 : ${menuList.first().price}원 X ${menuList.size}\n"
                         orderContentText.text = orderDataStr
                }

            orderContentText.text = orderContentText.text.trim()

            orderTotalPriceText.text =
                resourcesProvider.getString(
                    R.string.price ,
                    foodMenuList.map { it.price }.reduce{total , price -> total + price}
                )
        }
    }

}