package com.example.mvvmdeliverycloneapp.util.event

import com.example.mvvmdeliverycloneapp.screen.main.MainActivity
import kotlinx.coroutines.flow.MutableSharedFlow

class MenuChangeEventBus {

    val mainTabMenuFlow = MutableSharedFlow<MainActivity.MainTabMenu>()

    suspend fun changeMenu(menu : MainActivity.MainTabMenu){
        mainTabMenuFlow.emit(menu)
    }
}