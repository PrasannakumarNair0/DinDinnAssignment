package com.prasannakumar.dindinnassignment.dataClass

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ingredient(
    @SerializedName("requestId") var requestId : String,
    @SerializedName("tabTitle") var tabTitle : String,
    @SerializedName("productList") var productList : List<ProductList>
) :
    Parcelable

@Parcelize
data class ProductList(
    @SerializedName("image") var image : String,
    @SerializedName("productName") var productName : String,
    @SerializedName("quantity") var quantity : String,
    @SerializedName("productID") var productID : String
) :
    Parcelable