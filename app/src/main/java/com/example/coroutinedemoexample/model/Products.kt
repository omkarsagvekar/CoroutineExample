package com.example.coroutinedemoexample.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Products(
    val limit: Int,
    val products: List<Product>,
    val skip: Int,
    val total: Int
): Parcelable