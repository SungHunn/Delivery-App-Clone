package com.example.mvvmdeliverycloneapp.widget.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.mvvmdeliverycloneapp.model.Model
import com.example.mvvmdeliverycloneapp.screen.base.BaseViewModel
import com.example.mvvmdeliverycloneapp.util.provider.ResourcesProvider
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.AdapterListener

abstract class ModelViewHolder<M: Model>(
    binding : ViewBinding,
    protected val viewModel : BaseViewModel,
    protected val resourcesProvider: ResourcesProvider
): RecyclerView.ViewHolder(binding.root) {

    abstract fun reset()

    open fun bindData(model : M){
        reset()
    }

    abstract fun bindViews(model : M , adapterListener: AdapterListener)

}