package com.learning.studentsuperpower.fragments

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.learning.studentsuperpower.R
import com.learning.studentsuperpower.StandardInterface
import com.learning.studentsuperpower.adapters.HomeStdAdapter
import com.learning.studentsuperpower.databinding.LayoutHomeStdBinding
import com.learning.studentsuperpower.models.Standard

class HomeStdFragment(val cb: StandardInterface) : BottomSheetDialogFragment() {

    private lateinit var m: LayoutHomeStdBinding
    private lateinit var lm: GridLayoutManager
    private val stdList = ArrayList<Standard>()
    private lateinit var adapter: HomeStdAdapter

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)

        Log.d("TAG", "onCreateDialog: 3")
        return dialog
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        m = DataBindingUtil.inflate(inflater, R.layout.layout_home_std, container, false)
        init()
        return m.root
    }

    private fun init() {
        if (activity != null && !requireActivity().isFinishing && !requireActivity().isDestroyed) {
            lm = GridLayoutManager(requireContext(), 3)
            m.recyclerView.layoutManager = lm

            addStandards()
            adapter = HomeStdAdapter(stdList) { std ->
                adapter.notifyDataSetChanged()
                cb.onSuccess(std)
                dismiss()
            }
            m.recyclerView.adapter = adapter
        }
    }

    private fun addStandards() {
        stdList.add(Standard(1, "1"))
        stdList.add(Standard(2, "2"))
        stdList.add(Standard(3, "3"))
        stdList.add(Standard(4, "4"))
        stdList.add(Standard(5, "5"))
        stdList.add(Standard(6, "6"))
        stdList.add(Standard(7, "7"))
        stdList.add(Standard(8, "8"))
        stdList.add(Standard(9, "9"))
        stdList.add(Standard(10, "10"))
        stdList.add(Standard(11, "11"))
        stdList.add(Standard(12, "12"))
    }

}