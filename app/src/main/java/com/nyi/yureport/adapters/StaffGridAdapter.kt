package com.nyi.yureport.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.nyi.yureport.R
import com.nyi.yureport.vos.StaffVO
import org.w3c.dom.Text
import java.util.zip.Inflater

class StaffGridAdapter(val context: Context, val staffList : ArrayList<StaffVO>) : BaseAdapter() {

    lateinit var layoutInflater : LayoutInflater


    init {
        layoutInflater = LayoutInflater.from(context)
    }

    override fun getCount(): Int {
        return staffList.size
    }

    override fun getItem(position: Int): Any {
        return staffList.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        Log.d("adapter", " " + position)

        var view = layoutInflater.inflate(R.layout.item_staff, parent, false)

        var tvName : TextView = view.findViewById(R.id.tv_item_staff_name)
        var tvPhNo : TextView = view.findViewById(R.id.tv_item_staff_phone)
        var tvPosition : TextView = view.findViewById(R.id.tv_item_staff_position)
        var btnCall : Button = view.findViewById(R.id.btn_item_staff_call)

        tvName.text = staffList.get(position).name

        tvPhNo.text = staffList.get(position).phNo

        if(staffList.get(position).position.equals("")){
            tvPosition.visibility = View.GONE
        }else{
            tvPosition.visibility = View.VISIBLE
            tvPosition.text = staffList.get(position).position
        }

        return view
    }
}