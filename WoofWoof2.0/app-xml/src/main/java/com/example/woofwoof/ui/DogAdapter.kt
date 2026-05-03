package com.example.woofwoof.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.woofwoof.databinding.ItemDogBinding
import com.example.woofwoof.core.model.DogImage

class DogAdapter : ListAdapter<DogImage, DogAdapter.DogViewHolder>(DogDiffCallback()) {

    class DogViewHolder(private val binding: ItemDogBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dogImage: DogImage) {
            Glide.with(binding.root.context)
                .load(dogImage.imageUrl)
                .into(binding.ivDog)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = ItemDogBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DogDiffCallback : DiffUtil.ItemCallback<DogImage>() {
        override fun areItemsTheSame(oldItem: DogImage, newItem: DogImage): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: DogImage, newItem: DogImage): Boolean {
            return oldItem == newItem
        }
    }
}
