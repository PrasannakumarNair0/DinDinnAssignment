package com.prasannakumar.dindinnassignment.dataClass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    var requestId: String,
    var tabTitle: String,
    var productList: List<ProductList>
) :
    Parcelable

@Parcelize
data class ProductList(
    var image: String,
    var productName: String,
    var quantity: String,
    var productID: String
) :
    Parcelable