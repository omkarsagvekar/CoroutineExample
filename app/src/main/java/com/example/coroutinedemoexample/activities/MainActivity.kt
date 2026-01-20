package com.example.coroutinedemoexample.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coroutinedemoexample.R
import com.example.coroutinedemoexample.databinding.ActivityMainBinding
import com.example.coroutinedemoexample.model.Product
import com.example.coroutinedemoexample.viewModel.ProductState
import com.example.coroutinedemoexample.viewModel.ProductViewModel
import com.example.mystoreapp.adapters.StoreListAdapter
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var shopList: List<Product> = emptyList()
    lateinit var adapter: StoreListAdapter
    lateinit var mainView: View
    private val vm: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.rvProductList.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = StoreListAdapter { item ->
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_PRODUCT, item)
            startActivity(intent)
            //Toast.makeText(this, "CLicked", Toast.LENGTH_SHORT).show()
        }

        // binding.rvProductList.layoutManager = LinearLayoutManager(this)
        binding.rvProductList.adapter = adapter

        lifecycleScope.launch {
            vm.state.collectLatest { state ->
                when (state) {
                    is ProductState.Loading -> {
                        //binding.progressBar.visibility = android.view.View.VISIBLE
                        binding.shimmerLayout.visibility = View.VISIBLE
                        binding.shimmerLayout.startShimmer()
                        binding.rvProductList.visibility = View.GONE
                    }

                    is ProductState.Success -> {
                        //binding.progressBar.visibility = android.view.View.GONE
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.shimmerLayout.visibility = View.GONE
                        binding.shimmerLayout.stopShimmer()
                        adapter.submitList(state.items)
                        binding.rvProductList.visibility = View.VISIBLE
                    }

                    is ProductState.Error -> {
                        binding.swipeRefreshLayout.isRefreshing = false
                        binding.shimmerLayout.visibility = View.GONE
                        binding.shimmerLayout.stopShimmer()
                        binding.rvProductList.visibility = View.GONE
                    }
                }
            }
        }

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.shimmerLayout.visibility = View.VISIBLE
            binding.shimmerLayout.startShimmer()
            binding.swipeRefreshLayout.isRefreshing = true
            lifecycleScope.launch {
                vm.state.collectLatest { state ->
                    when (state) {
                        is ProductState.Loading -> {
                            //binding.progressBar.visibility = android.view.View.VISIBLE
                            //binding.swipeRefreshLayout.isRefreshing = true
                            binding.shimmerLayout.visibility = View.VISIBLE
                            binding.shimmerLayout.startShimmer()
                            binding.rvProductList.visibility = View.GONE
                        }

                        is ProductState.Success -> {
                            //binding.progressBar.visibility = android.view.View.GONE
                            binding.swipeRefreshLayout.isRefreshing = false
                            binding.shimmerLayout.visibility = View.GONE
                            binding.shimmerLayout.stopShimmer()
                            adapter.submitList(state.items)
                            binding.rvProductList.visibility = View.VISIBLE
                        }

                        is ProductState.Error -> {
                            binding.swipeRefreshLayout.isRefreshing = false
                            binding.shimmerLayout.visibility = View.GONE
                            binding.shimmerLayout.stopShimmer()
                            binding.rvProductList.visibility = View.GONE
                        }
                    }
                }
            }

        }
        //getProductData()
    }

    /*
    private fun getProductData() {
        lifecycleScope.launch(Dispatchers.Main) {
            try {
                val response = withContext(Dispatchers.IO) {
                    RetrofitClient.apiInstance.getProducts()
                }

                if (response.isSuccessful && response.body() != null) {
                    val productList = response.body()?.products!!
                    adapter = StoreListAdapter() { storeModel ->
                        // openItemOnNewActivity(storeModel)
                    }
                    binding.rvProductList.adapter = adapter
                } else {
                    Log.e("OMKAR_TESTING", "Error: ${response.message()}")
                }
                /*
                RetrofitClient.apiInstance.getProducts().enqueue(
                    object : Callback<Products>{
                        override fun onResponse(
                            call: Call<Products?>,
                            response: Response<Products?>
                        ) {
                            if (response.isSuccessful){
                                val productObj = response
                                shopList = productObj.body()?.products!!
                                adapter = StoreListAdapter(shopList, shopList) { storeModel ->
                                    //openItemOnNewActivity(storeModel)
                                }
                                binding.rvProductList.adapter = adapter

                            }
                        }

                        override fun onFailure(
                            call: Call<Products?>,
                            t: Throwable
                        ) {
                            Log.d("OMKAR_TESTING", "onFailure: "+t.message)
                        }

                    }
                )

                 */
            }catch (e: Exception) {
                Log.e("OMKAR_TESTING", "Exception: ${e.message}")
            }
        }
    }

     */
}