package com.nyi.yureport.viewfolders


import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import com.nyi.yureport.R
import com.nyi.yureport.vos.StaffVO

class StaffVH(itemView : View, val controllerStaffItem: ControllerStaffItem) : androidx.recyclerview.widget.RecyclerView.ViewHolder(itemView) {

    private var name : TextView
    private var phNo : TextView
    private var btnCall : ImageButton
    private var view : View

    init {
        view = itemView
        name = itemView.findViewById(R.id.tv_item_staff_name)
        phNo = itemView.findViewById(R.id.tv_item_staff_phone)
        btnCall = itemView.findViewById(R.id.btn_item_staff_call)


    }

    fun bindData(staff : StaffVO){
        name.text = staff.name + " ("+ staff.position + ")"
        phNo.text = staff.phNo

    }

    interface ControllerStaffItem{
        fun onClickStaff()
        fun onClcikCall()
    }
}