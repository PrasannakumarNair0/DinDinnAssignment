package com.prasannakumar.dindinnassignment.models

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.prasannakumar.dindinnassignment.dataClass.ProductList
import junit.framework.TestCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

@RunWith(AndroidJUnit4::class)
class DynamicFragmentViewModelTest : TestCase() {
    private lateinit var viewModel: DynamicFragmentViewModel
    private lateinit var filter: ArrayList<ProductList>
    private var filterList: MutableLiveData<ArrayList<ProductList>>? = null

    @Before
    public override fun setUp() {
        super.setUp()
        filter = ArrayList<ProductList>()
        viewModel = DynamicFragmentViewModel()
    }

    @Test
    fun getFilterValueEquals() {
        GlobalScope.launch {
            val obj = ProductList(
                image = "dummy url",
                productID = "Prasanna",
                productName = "123",
                quantity = "1234"
            )
            filter.add(obj)
            viewModel.filterValues("Prasanna", filter)
            filterList?.value = viewModel.getFilterValue().value
            assertEquals(filterList?.value, "Prasanna")
        }
    }

    @Test
    fun getFilterValueNotNull() {
        GlobalScope.launch {
            val obj = ProductList(
                image = "dummy url",
                productID = "Prasanna",
                productName = "123",
                quantity = "1234"
            )
            filter.add(obj)
            viewModel.filterValues("Prasanna", filter)
            filterList?.value = viewModel.getFilterValue().value
            assertNotNull(filterList?.value)
        }
    }

    @Test
    fun getFilterValueNull() {
        GlobalScope.launch {
            viewModel.filterValues("Prasanna", filter)
            filterList?.value = viewModel.getFilterValue().value
            assertNull(filterList?.value)
        }
    }
}