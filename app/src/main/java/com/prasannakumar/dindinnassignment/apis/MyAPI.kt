package com.prasannakumar.dindinnassignment.apis

import com.prasannakumar.dindinnassignment.dataClass.OrderList
import retrofit2.http.GET

interface MyAPI {
    @GET("orderList")
    suspend fun getOrderList(): List<OrderList>
}