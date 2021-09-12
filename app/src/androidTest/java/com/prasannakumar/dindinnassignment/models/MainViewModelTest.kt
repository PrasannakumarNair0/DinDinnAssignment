package com.prasannakumar.dindinnassignment.models

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prasannakumar.dindinnassignment.data.api.RetrofitBuilder
import com.prasannakumar.dindinnassignment.data.repository.MainRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class MainViewModelTest : TestCase() {
    private lateinit var viewModel: MainViewModel


    @Before
    public override fun setUp() {
        super.setUp()
        val apiHelper = RetrofitBuilder.ApiHelper(RetrofitBuilder.apiService)
        val repository = MainRepository(apiHelper = apiHelper)
        viewModel = MainViewModel(repository)
    }

    @Test
    fun getFoodOrderNotNull() {
        val test = viewModel.getFoodOrder()
        assertNotNull(test)

    }

    @Test
    fun getFoodOrderNull() {
        val test = viewModel.getFoodOrder().value
        assertNull(test)

    }
}