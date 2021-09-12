package com.prasannakumar.dindinnassignment.models

import androidx.lifecycle.MutableLiveData
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class SearchViewModelTest : TestCase() {
    lateinit var viewModel: SearchViewModel

    lateinit var query: MutableLiveData<String>

    @Before
    public override fun setUp() {
        super.setUp()
        query = MutableLiveData<String>()
        viewModel = SearchViewModel()
    }

    @Test
    fun setQueryNotNull() {

        GlobalScope.launch {
            viewModel.setQuery("hello")
            val checkData = viewModel.getQuery()
            assertNotNull(checkData)
        }
    }

    @Test
    fun setQueryNull() {

        GlobalScope.launch {
            val checkData = viewModel.getQuery()
            assertNull(checkData)
        }


    }
}