package com.orbitalsonic.recyclerviewswipetodelete

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.orbitalsonic.recyclerviewswipetodelete.databinding.ItemCountryBinding

class AdapterCountry :
    ListAdapter<CountryItem, AdapterCountry.CountryViewHolder>(DATA_COMPARATOR) {

    private var mListener: OnCountryItemClickListener? = null

    fun setOnItemClickListener(listener: OnCountryItemClickListener) {
        mListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding: ItemCountryBinding = DataBindingUtil.inflate(layoutInflater,R.layout.item_country,parent,false)
        return CountryViewHolder(binding, mListener!!)

    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    class CountryViewHolder(binding:ItemCountryBinding, listener: OnCountryItemClickListener) :
        RecyclerView.ViewHolder(binding.root) {
        private val mBinding = binding
        init {

            binding.item.setOnClickListener {
                val mPosition = adapterPosition
                listener.onItemClick(mPosition)
            }

        }

        fun bind(mCurrentItem: CountryItem) {
            mBinding.countryItemData = mCurrentItem
        }

    }


    companion object {
        private val DATA_COMPARATOR = object : DiffUtil.ItemCallback<CountryItem>() {
            override fun areItemsTheSame(oldItem: CountryItem, newItem: CountryItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: CountryItem, newItem: CountryItem): Boolean {
                return oldItem.countryCode == newItem.countryCode
            }
        }
    }

}