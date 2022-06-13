package com.mango.android.rickmortyapp.ui.activities.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mango.android.rickmortyapp.R
import com.mango.android.rickmortyapp.databinding.ItemCharacterBinding
import es.andres.bailen.domain.models.CharacterModel


class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(characterModel: CharacterModel?) {
        binding.character = characterModel
    }



    companion object {
        @JvmStatic
        fun from(parent: ViewGroup): CharacterViewHolder = CharacterViewHolder(
            ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }
}