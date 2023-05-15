package com.example.mvvmdeliverycloneapp.widget.adapter.viewholder.food

import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.mvvmdeliverycloneapp.R
import com.example.mvvmdeliverycloneapp.databinding.ViewholderFoodMenuBinding
import com.example.mvvmdeliverycloneapp.extensions.clear
import com.example.mvvmdeliverycloneapp.extensions.load
import com.example.mvvmdeliverycloneapp.model.restaurant.food.FoodModel
import com.example.mvvmdeliverycloneapp.screen.base.BaseViewModel
import com.example.mvvmdeliverycloneapp.util.provider.ResourcesProvider
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.AdapterListener
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.retaurant.FoodMenuListListener
import com.example.mvvmdeliverycloneapp.widget.adapter.viewholder.ModelViewHolder

class FoodMenuViewHolder(
    private val binding : ViewholderFoodMenuBinding,
    viewModel : BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<FoodModel>(binding, viewModel, resourcesProvider){

    override fun reset() = with(binding){
        foodImage.clear()
    }

    override fun bindViews(model: FoodModel, adapterListener: AdapterListener) {
        if (adapterListener is FoodMenuListListener){
            binding.root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
        }
    }

    override fun bindData(model: FoodModel) {
        super.bindData(model)
        with(binding){
            foodImage.load(model.imageUrl,24f, CenterCrop())
            foodTitleText.text = model.title
            foodDescriptionText.text = model.description
            priceText.text = resourcesProvider.getString(R.string.price , model.price)
        }
    }

}