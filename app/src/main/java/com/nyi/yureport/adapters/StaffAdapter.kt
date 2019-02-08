package com.nyi.yureport.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.nyi.yureport.R
import com.nyi.yureport.viewfolders.StaffVH
import com.nyi.yureport.vos.StaffVO

class StaffAdapter(val staffList : ArrayList<StaffVO>, val controllerCatItem: StaffVH.ControllerStaffItem) : RecyclerView.Adapter<StaffVH>() {

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): StaffVH {
        val v = LayoutInflater.from(p0.getContext()).inflate(R.layout.item_staff_1, p0, false)
        return StaffVH(v, controllerCatItem)
    }

    override fun getItemCount(): Int {
        return staffList.size
    }

    override fun onBindViewHolder(p0: StaffVH, p1: Int) {
        p0.bindData(staffList.get(p1))
    }

}