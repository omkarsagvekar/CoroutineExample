package com.example.coroutinedemoexample.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int,
    val title: String,
    val description: String,
    val price: String,
    val rating: String,
    val thumbnail: String


    /*
    val availabilityStatus: String,
    val brand: String,
    val category: String,
    val dimensions: Dimensions,
    val discountPercentage: Double,
    val images: List<String>,
    val meta: Meta,
    val minimumOrderQuantity: Int,
    val returnPolicy: String,
    val reviews: List<Review>,
    val shippingInformation: String,
    val sku: String,
    val stock: Int,
    val tags: List<String>,
    val thumbnail: String,
    val warrantyInformation: String,
    val weight: Int

     */
): Parcelable