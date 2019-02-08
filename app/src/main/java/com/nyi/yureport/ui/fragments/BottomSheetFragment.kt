package com.nyi.yureport.ui.fragments

import android.app.Dialog
import android.support.design.widget.BottomSheetDialogFragment
import android.support.design.widget.CoordinatorLayout
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.nyi.yureport.R
import com.nyi.yureport.ui.Report

/**
 * Created by IN-3442 on 22-Jul-18.
 */
class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun setupDialog(dialog: Dialog?, style: Int) {
        //Set the custom view
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_bottom_sheet, null)
        dialog?.setContentView(view)

        val params = (view.getParent() as View).layoutParams as CoordinatorLayout.LayoutParams

        (view.getParent() as View).setBackgroundResource(R.drawable.bg_bottom_sheet);

        val btn_report = view.findViewById<TextView>(R.id.tv_bottom_report)
        val btn_about = view.findViewById<TextView>(R.id.tv_bottom_about)

        btn_report.setOnClickListener {

            val intent = Report.newIntent(context!!)
            startActivity(intent)
        }

        btn_about.setOnClickListener {
            val dialog = InfoDialogFragment.newInstance()
            dialog.show(fragmentManager, "Info Fragment")
        }

        //val behavior = params.behavior
    }
}