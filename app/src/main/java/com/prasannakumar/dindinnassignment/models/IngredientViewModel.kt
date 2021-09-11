package com.prasannakumar.dindinnassignment.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.prasannakumar.dindinnassignment.data.repository.IngredientMainRepository
import com.prasannakumar.dindinnassignment.utils.Resource
import kotlinx.coroutines.Dispatchers

class IngredientViewModel (private val ingredientMainRepository: IngredientMainRepository) : ViewModel() {
    fun getIngredientDetails() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = ingredientMainRepository.getIngredientList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
