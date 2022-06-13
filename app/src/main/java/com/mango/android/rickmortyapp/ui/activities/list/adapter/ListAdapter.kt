package com.mango.android.rickmortyapp.ui.activities.list.adapter

import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import es.andres.bailen.domain.models.CharacterModel
import java.util.ArrayList

class CharacterAdapter(private val mListener: OnCharacterClickListener) :
    RecyclerView.Adapter<CharacterViewHolder>() {
    private var mCharacterList: List<CharacterModel?> = ArrayList(0)


    fun bindData(characters: List<CharacterModel?>?) {
        mCharacterList = ArrayList(characters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(mCharacterList[position], mListener)

    }

    override fun getItemCount(): Int {
        return mCharacterList.size
    }

    // --------------------------------------------------------------------------------------------
    // RecyclerView adapter
    // --------------------------------------------------------------------------------------------
    interface OnCharacterClickListener {
        fun onCharacterClicked(character: CharacterModel?, imageView: ImageView? = null)
    }

}