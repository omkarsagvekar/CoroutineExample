package com.example.coroutinedemoexample.activities

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.bumptech.glide.Glide
import com.example.coroutinedemoexample.R
import com.example.coroutinedemoexample.databinding.ActivityDetailBinding
import com.example.coroutinedemoexample.model.Product
import com.example.coroutinedemoexample.viewModel.ProductViewModel

class DetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding
    lateinit var detailActivityView: View
    private val viewModel: ProductViewModel by viewModels()

    companion object {
        const val EXTRA_PRODUCT = "extra_product"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        //binding = ActivityDetailBinding.inflate(layoutInflater)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        binding.lifecycleOwner = this
        binding.productdetail = viewModel
//        detailActivityView = binding.root
//        setContentView(detailActivityView)

//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }

        val productIntentExtra = intent.getParcelableExtra<Product>(EXTRA_PRODUCT)
        productIntentExtra?.let {
            // bind variable in layout

            viewModel.selectProduct(it)

            // load image by Glide (DataBinding expression for Glide requires BindingAdapter; simpler to load here)
            Glide.with(this)
                .load(it.thumbnail)
                .centerCrop()
                .into(binding.ivThumb)
        }
    }
}