package com.prasannakumar.dindinnassignment.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.prasannakumar.dindinnassignment.data.api.RetrofitBuilder
import com.prasannakumar.dindinnassignment.data.api.RetrofitBuilderForIngredient
import com.prasannakumar.dindinnassignment.data.repository.IngredientMainRepository
import com.prasannakumar.dindinnassignment.data.repository.MainRepository


class ViewModelFactory(private val apiHelper: RetrofitBuilder.ApiHelper) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(MainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
class ViewModelFactoryForIngredient(private val apiHelper: RetrofitBuilderForIngredient.ApiHelper) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(IngredientViewModel::class.java)) {
            return IngredientViewModel(IngredientMainRepository(apiHelper)) as T
        }
        throw IllegalArgumentException("Unknown class name")
    }

}
