package com.example.coroutinedemoexample.repository

import com.example.coroutinedemoexample.model.Product
import com.example.coroutinedemoexample.retrofit.RetrofitClient
import com.example.coroutinedemoexample.service.ApiService

class ProductRepository(private val api: ApiService = RetrofitClient.apiInstance) {

    suspend fun fetchAllProducts(): Result<List<Product>> {
        return try {
            val resp = api.getProducts()
            if (resp.isSuccessful) {
                Result.success(resp.body()?.products.orEmpty())
            } else {
                Result.failure(Exception("HTTP ${resp.code()} ${resp.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
