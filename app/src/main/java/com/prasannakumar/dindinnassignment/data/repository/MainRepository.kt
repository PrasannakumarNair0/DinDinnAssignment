package com.prasannakumar.dindinnassignment.data.repository

import com.prasannakumar.dindinnassignment.data.api.RetrofitBuilder

class MainRepository (private val apiHelper: RetrofitBuilder.ApiHelper) {
    suspend fun getFoodList()=apiHelper.getOrderDetails()
}