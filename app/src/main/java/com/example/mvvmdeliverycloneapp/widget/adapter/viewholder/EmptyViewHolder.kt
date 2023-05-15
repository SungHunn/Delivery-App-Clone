package com.example.mvvmdeliverycloneapp.widget.adapter.viewholder

import com.example.mvvmdeliverycloneapp.databinding.ViewholderEmptyBinding
import com.example.mvvmdeliverycloneapp.model.Model
import com.example.mvvmdeliverycloneapp.screen.base.BaseViewModel
import com.example.mvvmdeliverycloneapp.util.provider.ResourcesProvider
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.AdapterListener

class EmptyViewHolder(
    private val binding : ViewholderEmptyBinding,
    viewModel: BaseViewModel,
    resourcesProvider : ResourcesProvider
) : ModelViewHolder<Model>(binding, viewModel , resourcesProvider) {
    override fun reset() = Unit

    override fun bindViews(model: Model, adapterListener: AdapterListener) = Unit


}