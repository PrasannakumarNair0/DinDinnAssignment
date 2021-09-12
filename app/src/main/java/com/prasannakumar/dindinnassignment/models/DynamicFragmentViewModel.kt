package com.prasannakumar.dindinnassignment.models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.prasannakumar.dindinnassignment.dataClass.ProductList
import java.util.*

class DynamicFragmentViewModel:ViewModel() {
    private val filter = MutableLiveData<ArrayList<ProductList>>()

fun getFilterValue():LiveData<ArrayList<ProductList>>{
    return filter
}
    fun filterValues(text: String, productList: List<ProductList>) {
        val filterList = ArrayList<ProductList>()
        for (item in productList) {
            if (item.productName.lowercase(Locale.ROOT)
                    .contains(text.lowercase(Locale.ROOT))
            ) {

                filterList.add(item)
            }
        }
        filter.value=filterList
    }
}



