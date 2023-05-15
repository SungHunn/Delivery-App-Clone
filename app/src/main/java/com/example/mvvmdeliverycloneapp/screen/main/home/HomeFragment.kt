package com.example.mvvmdeliverycloneapp.screen.main.home

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.example.mvvmdeliverycloneapp.R
import com.example.mvvmdeliverycloneapp.data.entity.LocationLatLngEntity
import com.example.mvvmdeliverycloneapp.data.entity.MapSearchInfoEntity
import com.example.mvvmdeliverycloneapp.databinding.FragmentHomeBinding
import com.example.mvvmdeliverycloneapp.screen.base.BaseFragment
import com.example.mvvmdeliverycloneapp.screen.main.MainActivity
import com.example.mvvmdeliverycloneapp.screen.main.home.HomeFragment.Companion.newInstance
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.RestaurantCategory
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.RestaurantListFragment
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.RestaurantOrder
import com.example.mvvmdeliverycloneapp.screen.mylocation.MyLocationActivity
import com.example.mvvmdeliverycloneapp.screen.order.OrderMenuListActivity
import com.example.mvvmdeliverycloneapp.widget.adapter.RestaurantListFragmentPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.google.firebase.auth.FirebaseAuth
import org.koin.android.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {

    override val viewModel by viewModel<HomeViewModel>()

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    private lateinit var viewPagerAdapter: RestaurantListFragmentPagerAdapter

    private lateinit var locationManager: LocationManager

    private lateinit var myLocationListener: MyLocationListener

    private val firebaseAuth by lazy { FirebaseAuth.getInstance() }

    private val changeLocationLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                result.data?.getParcelableExtra<MapSearchInfoEntity>(HomeViewModel.MY_LOCATION_KEY)
                    ?.let { myLocationInfo ->
                        viewModel.loadReverseGeoInformation(myLocationInfo.locationLatLng)
                    }
            }
        }


    private val locationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val responsePermissions = permissions.entries.filter {
                it.key == android.Manifest.permission.ACCESS_FINE_LOCATION
                        || it.key == android.Manifest.permission.ACCESS_COARSE_LOCATION
            }
            if (responsePermissions.filter { it.value == true }.size == locationPermissions.size) {
                setMyLocationListener()
            } else {
                with(binding.locationTitleTextView) {
                    text = getString(R.string.please_request_location_permission)
                    setOnClickListener {
                        getMyLocation()
                    }
                }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.can_not_assigned_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    companion object {

        val locationPermissions = arrayOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
        )

        fun newInstance() = HomeFragment()

        const val TAG = "HomeFragment"
    }


    private fun initViewPager(locationLatLng: LocationLatLngEntity) = with(binding) {
        val restaurantCategories = RestaurantCategory.values()

        if (::viewPagerAdapter.isInitialized.not()) {
            orderChipGroup.isVisible=true

            val restaurantListFragmentList = restaurantCategories.map {
                RestaurantListFragment.newInstance(it, locationLatLng)
            }

            viewPagerAdapter = RestaurantListFragmentPagerAdapter(
                this@HomeFragment,
                restaurantListFragmentList,
                locationLatLng
            )
            viewPager.adapter = viewPagerAdapter
            viewPager.offscreenPageLimit = restaurantCategories.size
            TabLayoutMediator(tabLayout, viewPager) { tab, position ->
                tab.setText(restaurantCategories[position].categoryNameId)
            }.attach()
        }
        if (locationLatLng != viewPagerAdapter.locationLatLngEntity) {
            viewPagerAdapter.locationLatLngEntity = locationLatLng
            viewPagerAdapter.fragmentList.forEach {
                it.viewModel.setLocationLatLng(locationLatLng)
            }
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.checkMyBasket()
    }

    override fun observeData() {
        viewModel.homeStateLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is HomeState.Uninitialized -> {
                    getMyLocation()
                }
                is HomeState.Loading -> {
                    binding.locationLoading.isVisible = true
                    binding.locationTitleTextView.text = getString(R.string.loading)
                }
                is HomeState.Success -> {
                    binding.locationLoading.isGone = true
                    binding.locationTitleTextView.text = it.mapSearchInfoEntity.fullAddress
                    initViewPager(it.mapSearchInfoEntity.locationLatLng)
                    if (it.isLocationSame.not()) {
                        Toast.makeText(requireContext(), "위치가 맞는지 확인해주세요!", Toast.LENGTH_SHORT).show()
                    }
                }
                is HomeState.Error -> {
                    Toast.makeText(requireContext(), it.messageId, Toast.LENGTH_SHORT).show()
                }
            }
        }

        viewModel.foodMenuBasketLiveData.observe(this){
            if (it.isNotEmpty()){
                binding.basketButtonContainer.isVisible = true
                binding.basetCountTextView.text = getString(R.string.basket_count , it.size)
                binding.basketButton.setOnClickListener {
                    if(firebaseAuth.currentUser == null){

                        alertLoginNeed {
                            (requireActivity() as MainActivity).goToTab(MainActivity.MainTabMenu.MY)
                        }
                    }else{
                        startActivity(
                            OrderMenuListActivity.newIntent(requireContext())
                        )
                    }
                }
            }else{
                binding.basketButtonContainer.isGone = true
                binding.basketButton.setOnClickListener(null)
            }
        }
    }

    private fun alertLoginNeed(afterAction: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("로그인이 필요합니다.")
            .setMessage("주문하려면 로그인이 필요합니다. My탭으로 이동하시겠습니까?")
            .setPositiveButton("이동") { dialog, _ ->
                afterAction()
                dialog.dismiss()
            }
            .setNegativeButton("취소") { dialog, _ ->
                dialog.dismiss()
            }
            .create()
            .show()
    }

    override fun initViews() = with(binding) {
        locationTitleTextView.setOnClickListener {
            viewModel.getMapSearchInfo()?.let { mapInfo ->
                changeLocationLauncher.launch(
                    MyLocationActivity.newIntent(
                        requireContext(), mapInfo
                    )
                )
            }
        }
        orderChipGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.chipDefault -> {
                    chipInitialize.isGone = true
                    changeRestaurantOrder(RestaurantOrder.DEFAULT)
                }
                R.id.chipInitialize -> {
                    chipDefault.isChecked = true
                }
                R.id.chipLowDeliveryTip -> {
                    chipInitialize.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.LOW_DELIVERY_TIP)
                }
                R.id.chipFastDelivery -> {
                    chipInitialize.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.FAST_DELIVERY)
                }
                R.id.chipTopRate -> {
                    chipInitialize.isVisible = true
                    changeRestaurantOrder(RestaurantOrder.TOP_RATE)
                }
            }
        }
    }

    private fun changeRestaurantOrder(order : RestaurantOrder){
        viewPagerAdapter.fragmentList.forEach {
            it.viewModel.setRestaurantOrder(order)
        }

    }

    @SuppressLint("MissingPermission")
    private fun setMyLocationListener() {
        val minTime: Long = 1500
        val minDistance = 100f
        if (::myLocationListener.isInitialized.not()) {
            myLocationListener = MyLocationListener()
        }
        with(locationManager) {
            requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                minTime, minDistance, myLocationListener
            )
            requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                minTime, minDistance, myLocationListener
            )
        }
    }

    private fun getMyLocation() {
        if (::locationManager.isInitialized.not()) {
            locationManager =
                requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
        val isGpsUnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        if (isGpsUnabled) {
            locationPermissionLauncher.launch(locationPermissions)
        }
    }


    private fun removeLocationListener() {
        if (::locationManager.isInitialized && ::myLocationListener.isInitialized) {
            locationManager.removeUpdates(myLocationListener)
        }
    }

    inner class MyLocationListener : LocationListener {
        override fun onLocationChanged(location: Location) {
            //  binding.locationTitleTextView.text = "${location.latitude} , ${location.longitude}"
            viewModel.loadReverseGeoInformation(
                LocationLatLngEntity(
                    location.latitude,
                    location.longitude
                )
            )
            removeLocationListener()
        }

    }
}