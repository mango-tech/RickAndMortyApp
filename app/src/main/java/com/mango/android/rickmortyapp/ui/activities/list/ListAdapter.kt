package com.mango.android.rickmortyapp.ui.activities.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mango.android.rickmortyapp.R
import es.andres.bailen.domain.models.CharacterModel
import java.util.ArrayList

class CharacterAdapter(private val mListener: OnCharacterClickListener) :
    RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {
    private var mCharacterList: List<CharacterModel?> = ArrayList(0)

    inner class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var mName: TextView

        init {
            mName = view.findViewById(R.id.tv_name)
        }
    }

    fun bindData(characters: List<CharacterModel?>?) {
        mCharacterList = ArrayList(characters)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.mName.text = mCharacterList.get(position)!!.name
        holder.itemView.setOnClickListener {
            mListener.onCharacterClicked(
                mCharacterList[position]
            )
        }
    }

    override fun getItemCount(): Int {
        return mCharacterList.size
    }

    // --------------------------------------------------------------------------------------------
    // RecyclerView adapter
    // --------------------------------------------------------------------------------------------
    interface OnCharacterClickListener {
        fun onCharacterClicked(character: CharacterModel?)
    }

}