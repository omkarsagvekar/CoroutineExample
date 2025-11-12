package com.example.coroutinedemoexample.service

import com.example.coroutinedemoexample.model.Products
import com.example.coroutinedemoexample.model.UserInfo
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("/products")
//    fun getProducts(): Call<Products>
    suspend fun getProducts(): Response<Products>

    suspend fun getUserData(): Response<UserInfo>
}