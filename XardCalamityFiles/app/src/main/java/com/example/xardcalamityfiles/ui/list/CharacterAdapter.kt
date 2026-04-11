package com.example.xardcalamityfiles.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.xardcalamityfiles.data.model.CharacterWithAbilities
import com.example.xardcalamityfiles.databinding.ItemCharacterBinding

class CharacterAdapter(private val onItemClick: (Long) -> Unit) :
    ListAdapter<CharacterWithAbilities, CharacterAdapter.CharacterViewHolder>(CharacterDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CharacterViewHolder(
        private val binding: ItemCharacterBinding,
        private val onItemClick: (Long) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharacterWithAbilities) {
            val chara = item.character
            binding.tvName.text = chara.name
            binding.tvClassSubclass.text = "${chara.characterClass} • ${chara.subclass}"
            
            binding.ivProfile.load(chara.profilePictureUri) {
                crossfade(true)
                transformations(CircleCropTransformation())
                // fallback and error states can be added here
            }

            binding.root.setOnClickListener {
                onItemClick(chara.id)
            }
        }
    }

    class CharacterDiffCallback : DiffUtil.ItemCallback<CharacterWithAbilities>() {
        override fun areItemsTheSame(oldItem: CharacterWithAbilities, newItem: CharacterWithAbilities): Boolean {
            return oldItem.character.id == newItem.character.id
        }

        override fun areContentsTheSame(oldItem: CharacterWithAbilities, newItem: CharacterWithAbilities): Boolean {
            return oldItem == newItem
        }
    }
}
