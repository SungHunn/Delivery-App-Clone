package com.example.mvvmdeliverycloneapp.widget.adapter.viewholder.review

import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.mvvmdeliverycloneapp.databinding.ViewholderRestaurantReviewBinding
import com.example.mvvmdeliverycloneapp.extensions.clear
import com.example.mvvmdeliverycloneapp.extensions.load
import com.example.mvvmdeliverycloneapp.model.restaurant.review.RestaurantReviewModel
import com.example.mvvmdeliverycloneapp.screen.base.BaseViewModel
import com.example.mvvmdeliverycloneapp.util.provider.ResourcesProvider
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.AdapterListener
import com.example.mvvmdeliverycloneapp.widget.adapter.viewholder.ModelViewHolder

class RestaurantReviewViewHolder(
    private val binding: ViewholderRestaurantReviewBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<RestaurantReviewModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = with(binding) {
        reviewThumbnailImage.clear()
        reviewThumbnailImage.isGone = true
    }

    override fun bindData(model: RestaurantReviewModel) {
        super.bindData(model)
        with(binding) {
            if (model.thumbnailImageUri != null) {
                reviewThumbnailImage.isVisible = true
                reviewThumbnailImage.load(model.thumbnailImageUri.toString())
            } else {
                reviewThumbnailImage.isGone = true
            }

            reviewTitleText.text = model.title
            reviewText.text = model.description
            ratingBar.rating = model.grade
        }
    }

    override fun bindViews(model: RestaurantReviewModel, adapterListener: AdapterListener) = Unit

}