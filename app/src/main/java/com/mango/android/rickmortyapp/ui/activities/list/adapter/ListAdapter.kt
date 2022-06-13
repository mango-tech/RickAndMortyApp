package com.mango.android.rickmortyapp.ui.activities.list.adapter

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import es.andres.bailen.domain.models.CharacterModel
import java.util.ArrayList

class CharacterAdapter(private val mListener: OnCharacterClickListener) :
    PagingDataAdapter<CharacterModel, CharacterViewHolder>(DIFF_CHARACTER_UTIL) {

    /*fun bindData(characters: List<CharacterModel?>?) {
        mCharacterList = ArrayList(characters)
        notifyDataSetChanged()
    }*/

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position), mListener)
    }
/*
    override fun getItemCount(): Int {
        return mCharacterList.size
    }*/

    companion object {
        val DIFF_CHARACTER_UTIL = object: DiffUtil.ItemCallback<CharacterModel>() {
            override fun areItemsTheSame(
                oldItem: CharacterModel,
                newItem: CharacterModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: CharacterModel,
                newItem: CharacterModel
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    // --------------------------------------------------------------------------------------------
    // RecyclerView adapter
    // --------------------------------------------------------------------------------------------
    interface OnCharacterClickListener {
        fun onCharacterClicked(character: CharacterModel?, imageView: ImageView, nameTxtView: TextView)
    }

}