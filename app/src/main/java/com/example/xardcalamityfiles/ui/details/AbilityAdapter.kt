package com.example.xardcalamityfiles.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.xardcalamityfiles.data.model.Ability
import com.example.xardcalamityfiles.databinding.ItemAbilityBinding
import io.noties.markwon.Markwon

class AbilityAdapter(private val markwon: Markwon) :
    ListAdapter<Ability, AbilityAdapter.AbilityViewHolder>(AbilityDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbilityViewHolder {
        val binding = ItemAbilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AbilityViewHolder(binding, markwon)
    }

    override fun onBindViewHolder(holder: AbilityViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class AbilityViewHolder(
        private val binding: ItemAbilityBinding,
        private val markwon: Markwon
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ability: Ability) {
            binding.tvAbilityName.text = ability.name
            binding.tvAbilityType.text = ability.type

            // Render Markdown for description
            markwon.setMarkdown(binding.tvAbilityDescription, ability.description)
            
            binding.ivAbilityIcon.load(ability.iconUri) {
                crossfade(true)
            }
        }
    }

    class AbilityDiffCallback : DiffUtil.ItemCallback<Ability>() {
        override fun areItemsTheSame(oldItem: Ability, newItem: Ability): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Ability, newItem: Ability): Boolean {
            return oldItem == newItem
        }
    }
}
