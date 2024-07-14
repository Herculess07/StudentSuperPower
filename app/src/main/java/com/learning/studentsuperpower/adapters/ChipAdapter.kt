package com.learning.studentsuperpower.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.studentsuperpower.R
import com.learning.studentsuperpower.Utils.addBackground
import com.learning.studentsuperpower.databinding.ItemChipBinding

class ChipAdapter(
    val chipsArray: ArrayList<String>
) : RecyclerView.Adapter<ChipAdapter.VhChipsBinding>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhChipsBinding {
        return VhChipsBinding(
            ItemChipBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return chipsArray.size
    }

    override fun onBindViewHolder(holder: VhChipsBinding, position: Int) {
        val chipData = chipsArray[position]
        holder.m.chip.text = chipData
        holder.m.chip.addBackground(R.color.grey)
    }

    inner class VhChipsBinding(val m: ItemChipBinding) : RecyclerView.ViewHolder(m.root)
}