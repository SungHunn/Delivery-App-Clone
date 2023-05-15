package com.example.mvvmdeliverycloneapp.screen.main.like


import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.mvvmdeliverycloneapp.databinding.FragmentRestaurantLikeListBinding
import com.example.mvvmdeliverycloneapp.model.restaurant.RestaurantModel
import com.example.mvvmdeliverycloneapp.screen.base.BaseFragment
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.detail.RestaurantDetailActivity
import com.example.mvvmdeliverycloneapp.util.provider.ResourcesProvider
import com.example.mvvmdeliverycloneapp.widget.adapter.ModelRecyclerAdapter
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.retaurant.RestaurantLikeListListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class RestaurantLikeListFragment :
    BaseFragment<RestaurantLikeListViewModel, FragmentRestaurantLikeListBinding>() {

    override fun getViewBinding(): FragmentRestaurantLikeListBinding =
        FragmentRestaurantLikeListBinding.inflate(layoutInflater)

    override val viewModel by viewModel<RestaurantLikeListViewModel>()

    private val resourcesProvider by inject<ResourcesProvider>()


    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantModel, RestaurantLikeListViewModel>(
            listOf(),
            viewModel,
            resourcesProvider,
            adapterListener = object :
                RestaurantLikeListListener {

                override fun onDisLikeItem(model: RestaurantModel) {
                    viewModel.dislikeRestaurant(model.toEntity())
                }


                override fun onLikeItem(model: RestaurantModel) {

                }

                override fun onClickItem(model: RestaurantModel) {
                    startActivity(
                        RestaurantDetailActivity.newIntent(requireContext(), model.toEntity())
                    )
                }


            })
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    override fun observeData() {
        viewModel.restaurantListLiveData.observe(viewLifecycleOwner) {
            checkListEmpty(it)
        }
    }

    private fun checkListEmpty(restaurantList : List<RestaurantModel>){
        val isEmpty = restaurantList.isEmpty()
        binding.recyclerView.isGone = isEmpty
        binding.emptyResultTextView.isVisible = isEmpty
        if (isEmpty.not()){
            adapter.submitList(restaurantList)
        }
    }

    companion object {
        fun newInstance() = RestaurantLikeListFragment()

        const val TAG = "restaurantLikeListFragment"
    }

}