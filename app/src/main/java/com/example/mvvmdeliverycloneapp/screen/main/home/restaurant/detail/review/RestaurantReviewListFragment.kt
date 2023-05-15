package com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.detail.review


import androidx.core.os.bundleOf
import com.example.mvvmdeliverycloneapp.databinding.FragmentListBinding
import com.example.mvvmdeliverycloneapp.model.restaurant.review.RestaurantReviewModel
import com.example.mvvmdeliverycloneapp.screen.base.BaseFragment
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import com.example.mvvmdeliverycloneapp.util.provider.ResourcesProvider
import com.example.mvvmdeliverycloneapp.widget.adapter.ModelRecyclerAdapter
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.AdapterListener
import com.example.mvvmdeliverycloneapp.widget.adapter.listener.retaurant.FoodMenuListListener
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class RestaurantReviewListFragment :
    BaseFragment<RestaurantReviewListViewModel, FragmentListBinding>() {

    override fun getViewBinding(): FragmentListBinding = FragmentListBinding.inflate(layoutInflater)

    override val viewModel by viewModel<RestaurantReviewListViewModel> {
        parametersOf(
            arguments?.getString(RESTAURANT_TITLE_KEY)
        )
    }

    private val resourcesProvider by inject<ResourcesProvider>()

    private val adapter by lazy {
        ModelRecyclerAdapter<RestaurantReviewModel, RestaurantReviewListViewModel>(
            listOf(),
            viewModel,
            resourcesProvider,
            adapterListener = object : AdapterListener{

            })
    }

    override fun initViews() {
        binding.recyclerView.adapter = adapter
    }

    override fun observeData() = viewModel.reviewStateLiveData.observe(viewLifecycleOwner) {
        when (it) {
            is RestaurantReviewState.Success -> {
                handleSuccess(it)
            }
            else -> Unit
        }
    }

    private fun handleSuccess(state: RestaurantReviewState.Success) {
        adapter.submitList(state.reviewList)
    }

    companion object {

        const val RESTAURANT_TITLE_KEY = "restaurantTitle"


        fun newInstance(restaurantTitle: String): RestaurantReviewListFragment {
            val bundle = bundleOf(
                RESTAURANT_TITLE_KEY to restaurantTitle,
            )
            return RestaurantReviewListFragment().apply {
                arguments = bundle
            }
        }
    }
}