package com.prasannakumar.dindinnassignment.data.repository

import com.prasannakumar.dindinnassignment.data.api.RetrofitBuilderForIngredient

class IngredientMainRepository (private val apiHelper: RetrofitBuilderForIngredient.ApiHelper){
    suspend fun getIngredientList()=apiHelper.getIngredient()
}