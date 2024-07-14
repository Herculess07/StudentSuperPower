package com.learning.studentsuperpower.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.learning.studentsuperpower.databinding.ItemStandardBinding
import com.learning.studentsuperpower.models.Standard

class HomeStdAdapter(
    private val data: List<Standard>,
    private val cb: (String) -> Unit
) : RecyclerView.Adapter<HomeStdAdapter.VhBinding>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhBinding {
        return VhBinding(
            ItemStandardBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: VhBinding, position: Int) {
        val standard = data[position]
        holder.m.lblStandardName.text = standard.stdName
        holder.m.option.setOnClickListener {
            cb(standard.stdName)
        }

    }

    inner class VhBinding(val m: ItemStandardBinding) : RecyclerView.ViewHolder(m.root)
}
