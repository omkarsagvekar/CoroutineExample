package com.example.coroutinedemoexample.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Dimensions(
    val depth: Double,
    val height: Double,
    val width: Double
): Parcelable