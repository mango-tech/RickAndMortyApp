package com.mango.android.rickmortyapp.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mango.android.domain.entity.CharacterEntity
import com.mango.android.rickmortyapp.databinding.ItemCharacterBinding

class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.ViewHolder>() {

    private var list = mutableListOf<CharacterEntity>()
    var onDisplayLastItem: (() -> Unit)? = null
    var onItemClick: ((CharacterEntity) -> Unit)? = null

    class ViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CharacterEntity) {
            binding.root.tag = item
            binding.textViewName.text = item.name
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCharacterBinding.inflate(LayoutInflater.from(parent.context)).also {
            it.root.setOnClickListener { view ->
                if (view.tag is CharacterEntity)
                    onItemClick?.invoke(view.tag as CharacterEntity)
            }
        })
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list.size > position)
            holder.bind(list[position])

        if (position == list.size - 1)
            onDisplayLastItem?.invoke()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setData(data: List<CharacterEntity>) {
        val itemsHad = list.size
        val itemsHave = data.size

        list.clear()
        list.addAll(data)

        if (itemsHad > itemsHave)
            notifyItemRangeRemoved(itemsHave, itemsHad - itemsHave)
        else
            notifyItemRangeInserted(itemsHad, itemsHave - itemsHad)
    }
}