package com.prasannakumar.dindinnassignment.models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.prasannakumar.dindinnassignment.data.repository.MainRepository
import com.prasannakumar.dindinnassignment.utils.Resource
import kotlinx.coroutines.Dispatchers


class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {
    fun getFoodOrder() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(Resource.success(data = mainRepository.getFoodList()))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error Occurred!"))
        }
    }
}
