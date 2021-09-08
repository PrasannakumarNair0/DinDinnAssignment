package com.prasannakumar.dindinnassignment.dataClass

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class OrderList(
    var status: Status,
    var data: List<Data>,
    var id: String
) :
    Parcelable

@Parcelize
data class Status(
    val success: Boolean,
    val statusCode: Int,
    var message: String
) :
    Parcelable

@Parcelize
data class Data(
    val id: Int,
    val title: String,
    var addon: List<Addon>,
    val quantity: Int,
    val created_at: String,
    val alerted_at: String,
    val expired_at: String
) :
    Parcelable

@Parcelize
data class Addon(
    val id: Int,
    val title: String,
    var quantity: Int
) :
    Parcelable