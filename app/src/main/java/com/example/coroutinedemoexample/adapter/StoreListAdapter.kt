package com.example.mystoreapp.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.coroutinedemoexample.databinding.StoreItemBinding
import com.example.coroutinedemoexample.model.Product
import com.example.coroutinedemoexample.model.Products
import kotlin.collections.filter

class StoreListAdapter(var onClick: (Product) -> Unit):
    ListAdapter<Product, StoreListAdapter.StoreViewHolder>(DIFF) {
    //lateinit var secondaryList: List<StoreModel>
    inner class StoreViewHolder(val binding: StoreItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(storeModel: Product){
            binding.tvPrize.text = "${storeModel.price}"
            if (storeModel.thumbnail.isNotEmpty()){
                Glide.with(binding.ivItemImg.context).load(storeModel.thumbnail).into(binding.ivItemImg)

            }
            binding.root.setOnClickListener {
                onClick(storeModel)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoreViewHolder {
        val binding = StoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoreViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: StoreViewHolder,
        position: Int
    ) = holder.bind((getItem(position)))

    companion object {
        val DIFF = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product) =
                oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: Product, newItem: Product) =
                oldItem == newItem
        }
    }


    //override fun getItemCount(): Int = storeList?.size ?: 0

    /*
    fun filter(_storeList: List<Product>?, filterStr: String){
        val filterString = filterStr
        storeList = _storeList!!
//        secondaryList = emptyList()
//        //storeList?.filter {  }
//        if (storeList != null) {
//            for(updatedFilterList in storeList){
//                if (updatedFilterList.price.toString().equals(filterString)){
//                    secondaryList.
//                }
//            }
//            storeList = updatedList
//        }
        val resultFilterList = storeList?.filter {
                storeModel -> storeModel.category.equals(filterString)
        }
        storeList = resultFilterList
        notifyDataSetChanged()
    }

     */
}