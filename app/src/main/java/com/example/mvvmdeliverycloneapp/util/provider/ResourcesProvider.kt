package com.example.mvvmdeliverycloneapp.util.provider

import android.content.res.ColorStateList
import androidx.annotation.ColorRes
import androidx.annotation.StringRes

interface ResourcesProvider {

    fun getString(@StringRes resId: Int): String

    fun getString(@StringRes resId: Int, vararg formatArgs: Any): String

    fun getColorStateList(@ColorRes resId: Int): ColorStateList

    fun getColor(@ColorRes resId : Int) : Int


}