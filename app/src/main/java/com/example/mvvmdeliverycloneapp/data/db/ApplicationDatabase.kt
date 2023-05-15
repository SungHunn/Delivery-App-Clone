package com.example.mvvmdeliverycloneapp.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mvvmdeliverycloneapp.data.db.dao.FoodMenuBasketDao
import com.example.mvvmdeliverycloneapp.data.db.dao.LocationDao
import com.example.mvvmdeliverycloneapp.data.db.dao.RestaurantDao
import com.example.mvvmdeliverycloneapp.data.entity.LocationLatLngEntity
import com.example.mvvmdeliverycloneapp.data.entity.RestaurantEntity
import com.example.mvvmdeliverycloneapp.data.entity.RestaurantFoodEntity


@Database(
    entities = [LocationLatLngEntity::class,RestaurantEntity::class,RestaurantFoodEntity::class],
    version = 1,
    exportSchema = false
)

abstract class ApplicationDatabase: RoomDatabase() {

    companion object {
        const val DB_NAME = "ApplicationDataBase.db"
    }

    abstract fun LocationDao(): LocationDao

    abstract fun RestaurantDao() : RestaurantDao

    abstract fun FoodMenuBasketDao() : FoodMenuBasketDao


}