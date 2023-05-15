package com.example.mvvmdeliverycloneapp.di

import com.example.mvvmdeliverycloneapp.data.entity.LocationLatLngEntity
import com.example.mvvmdeliverycloneapp.data.entity.MapSearchInfoEntity
import com.example.mvvmdeliverycloneapp.data.entity.RestaurantEntity
import com.example.mvvmdeliverycloneapp.data.entity.RestaurantFoodEntity
import com.example.mvvmdeliverycloneapp.data.preference.AppPreferenceManager
import com.example.mvvmdeliverycloneapp.data.repository.map.DefaultMapRepository
import com.example.mvvmdeliverycloneapp.data.repository.map.MapRepository
import com.example.mvvmdeliverycloneapp.data.repository.user.DefaultUserRepository
import com.example.mvvmdeliverycloneapp.data.repository.order.DefaultOrderRepository
import com.example.mvvmdeliverycloneapp.data.repository.order.OrderRepository
import com.example.mvvmdeliverycloneapp.data.repository.restaurant.DefaultRestaurantRepository
import com.example.mvvmdeliverycloneapp.data.repository.restaurant.RestaurantRepository
import com.example.mvvmdeliverycloneapp.data.repository.restaurant.food.DefaultRestaurantFoodRepository
import com.example.mvvmdeliverycloneapp.data.repository.restaurant.food.RestaurantFoodRepository
import com.example.mvvmdeliverycloneapp.data.repository.restaurant.review.DefaultRestaurantReviewRepository
import com.example.mvvmdeliverycloneapp.data.repository.restaurant.review.RestaurantReviewRepository
import com.example.mvvmdeliverycloneapp.data.repository.user.UserRepository
import com.example.mvvmdeliverycloneapp.screen.main.home.HomeViewModel
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.RestaurantCategory
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.RestaurantListViewModel
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.detail.RestaurantDetailViewModel
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.detail.menu.RestaurantMenuListViewModel
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.detail.review.RestaurantReviewListViewModel
import com.example.mvvmdeliverycloneapp.screen.main.like.RestaurantLikeListViewModel
import com.example.mvvmdeliverycloneapp.screen.main.my.MyViewModel
import com.example.mvvmdeliverycloneapp.screen.mylocation.MyLocationViewModel
import com.example.mvvmdeliverycloneapp.screen.order.OrderMenuListViewModel
import com.example.mvvmdeliverycloneapp.util.event.MenuChangeEventBus
import com.example.mvvmdeliverycloneapp.util.provider.DefaultResourcesProvider
import com.example.mvvmdeliverycloneapp.util.provider.ResourcesProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    //viewModel { MainViewModel() }
    viewModel { HomeViewModel(get(), get(), get()) }
    viewModel { RestaurantLikeListViewModel(get()) }
    viewModel { MyViewModel(get(), get(), get()) }

    factory { (restaurantCategory: RestaurantCategory, locationLatLngEntity: LocationLatLngEntity) ->
        RestaurantListViewModel(restaurantCategory, locationLatLngEntity, get())
    }

    viewModel { (mapSearchInfoEntity: MapSearchInfoEntity) ->
        MyLocationViewModel(mapSearchInfoEntity, get(), get())
    }

    viewModel { (restaurantEntity: RestaurantEntity) -> RestaurantDetailViewModel(restaurantEntity, get(), get()) }

    viewModel { (restaurantId: Long, restaurantFoodList: List<RestaurantFoodEntity>) ->
        RestaurantMenuListViewModel(restaurantId, restaurantFoodList, get())
    }

    viewModel { (restaurantTitle: String) -> RestaurantReviewListViewModel(restaurantTitle, get()) }

    viewModel { OrderMenuListViewModel(get(), get(), get()) }

    //viewModel { GalleryViewModel(get()) }

    single<MapRepository> { DefaultMapRepository(get(), get()) }
    single<RestaurantRepository> { DefaultRestaurantRepository(get(), get(), get()) }
    single<UserRepository> { DefaultUserRepository(get(), get(), get()) }
    single<RestaurantFoodRepository> { DefaultRestaurantFoodRepository(get(), get(), get()) }
    single<OrderRepository> { DefaultOrderRepository(get(), get()) }
    single<RestaurantReviewRepository> { DefaultRestaurantReviewRepository(get() , get()) }
    //single { GalleryPhotoRepository(androidApplication()) }

    single { provideGsonConverterFactory() }
    single { buildOkHttpClient() }

    single(named("map")) { provideMapRetrofit(get(), get()) }
    single(named("food")) { provideFoodRetrofit(get(), get()) }

    single { provideMapApiService(get(qualifier = named("map"))) }
    single { provideFoodApiService(get(qualifier = named("food"))) }

    single { provideDB(androidApplication()) }
    single { provideLocationDao(get()) }
    single { provideFoodMenuBasketDao(get()) }
    single { provideRestaurantDao(get()) }

    single<ResourcesProvider> { DefaultResourcesProvider(androidApplication()) }
    single { AppPreferenceManager(androidApplication()) }

    single { MenuChangeEventBus() }

    single { Dispatchers.IO }
    single { Dispatchers.Main }

    single { Firebase.firestore }
    single { Firebase.storage }
    single { FirebaseAuth.getInstance() }
    single { FirebaseStorage.getInstance()}

}