package com.prasannakumar.dindinnassignment.models

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prasannakumar.dindinnassignment.data.api.RetrofitBuilderForIngredient
import com.prasannakumar.dindinnassignment.data.repository.IngredientMainRepository
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

class IngredientViewModelTest : TestCase() {
    lateinit var viewModel: IngredientViewModel

    @Before
    public override fun setUp() {
        super.setUp()
        val apiHelper =
            RetrofitBuilderForIngredient.ApiHelper(RetrofitBuilderForIngredient.apiService)
        val repository = IngredientMainRepository(apiHelper)
        viewModel = IngredientViewModel(repository)
    }

    @Test
    fun getIngredientDetailsNotNull() {
        val data = viewModel.getIngredientDetails()

        assertNotNull(data)
    }

    @Test
    fun getIngredientDetailsNull() {
        val data = viewModel.getIngredientDetails().value

        assertNull(data)
    }
}