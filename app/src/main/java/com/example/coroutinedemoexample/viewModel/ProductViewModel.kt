package com.example.coroutinedemoexample.viewModel

import android.annotation.SuppressLint
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coroutinedemoexample.model.Product
import com.example.coroutinedemoexample.model.Products
import com.example.coroutinedemoexample.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

sealed class ProductState(){
    object Loading : ProductState()
    data class Success(val items: List<Product>) : ProductState()
    data class Error(val message: String) : ProductState()
}
class ProductViewModel(private val repo: ProductRepository = ProductRepository()): ViewModel() {
    private val _state = MutableStateFlow<ProductState>(ProductState.Loading)
    val state: StateFlow<ProductState> = _state

    init { loadProducts() }

    @SuppressLint("DefaultLocale")
    fun loadProducts() {
        _state.value = ProductState.Loading
        viewModelScope.launch {
            val res = repo.fetchAllProducts()
            res.fold(onSuccess = { list ->
                // map network -> UI model (outside adapter)
                val uiList = list.map { net ->
                    Product(
                        id = net.id,
                        title = net.title.trim().replaceFirstChar { it.uppercase() },
                        description = net.description.trim(),
                        price = "₹${net.price}",
                        rating = net.rating.toString(),
                        thumbnail = net.thumbnail
                    )
                }
                _state.value = ProductState.Success(uiList)
            }, onFailure = { err ->
                _state.value = ProductState.Error(err.message ?: "Something went wrong")
            })
        }
    }

    private val _selectedProduct = MutableStateFlow<Product?>(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    fun selectProduct(product: Product) {
        _selectedProduct.value = product
    }

}