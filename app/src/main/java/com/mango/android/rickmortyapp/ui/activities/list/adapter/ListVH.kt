package com.mango.android.rickmortyapp.ui.activities.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mango.android.rickmortyapp.databinding.ItemCharacterBinding
import es.andres.bailen.domain.models.CharacterModel


class CharacterViewHolder(private val binding: ItemCharacterBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(characterModel: CharacterModel?, listener: CharacterAdapter.OnCharacterClickListener) {
        binding.character = characterModel
        binding.root.setOnClickListener {
            listener.onCharacterClicked(
                characterModel, binding.imgAvatar, binding.tvName
            )
        }
    }


    companion object {
        @JvmStatic
        fun from(parent: ViewGroup): CharacterViewHolder = CharacterViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}