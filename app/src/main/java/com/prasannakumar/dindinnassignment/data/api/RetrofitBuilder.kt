package com.prasannakumar.dindinnassignment.data.api

import com.prasannakumar.dindinnassignment.BuildConfig
import com.prasannakumar.dindinnassignment.apis.MyAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build() //Doesn't require the adapter
    }

    val apiService: MyAPI = getRetrofit().create(MyAPI::class.java)

    class ApiHelper(private val apiService: MyAPI) {
        suspend fun getOrderDetails()=apiService.getOrderList()
    }

}