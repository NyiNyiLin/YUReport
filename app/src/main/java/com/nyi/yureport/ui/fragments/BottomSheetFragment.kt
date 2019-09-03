//package com.nyi.yureport.ui.fragments
//
//import android.app.Dialog
//import android.content.Context
//import android.content.Intent
//import android.net.ConnectivityManager
//import android.net.NetworkInfo
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.TextView
//import android.widget.Toast
//import androidx.coordinatorlayout.widget.CoordinatorLayout
//import androidx.core.content.ContextCompat.startActivity
//import com.nyi.yureport.R
//import com.nyi.yureport.ui.PrivacyPolicyActivity
//import com.nyi.yureport.ui.Report
//
///**
// * Created by IN-3442 on 22-Jul-18.
// */
//class BottomSheetFragment : BottomSheetFragment() {
//
//
//    override fun setupDialog(dialog: Dialog?, style: Int) {
//        //Set the custom view
//        val view = LayoutInflater.from(context).inflate(R.layout.fragment_bottom_sheet, null)
//        dialog?.setContentView(view)
//
//        val params = (view.getParent() as View).layoutParams as CoordinatorLayout.LayoutParams
//
//        (view.getParent() as View).setBackgroundResource(R.drawable.bg_bottom_sheet);
//
//        val btn_report = view.findViewById<TextView>(R.id.tv_bottom_report)
//        val btn_about = view.findViewById<TextView>(R.id.tv_bottom_about)
//        val btn_privacy_policy = view.findViewById<TextView>(R.id.tv_bottom_privacy_policy)
//
//        btn_report.setOnClickListener {
//            onClickReport()
//        }
//
//        btn_about.setOnClickListener {
//            val dialog = InfoDialogFragment.newInstance()
//            dialog.show(fragmentManager, "Info Fragment")
//        }
//
//        btn_privacy_policy.setOnClickListener {
//
//            val intent = Intent(context, PrivacyPolicyActivity::class.java)
//            startActivity(intent)
//        }
//
//        //val behavior = params.behavior
//    }
//
//    private fun onClickReport(){
//        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
//        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
//        val isConnected :Boolean = activeNetwork?.isConnected == true
//
//        if(isConnected){
//            val intent = Report.newIntent(context!!)
//            startActivity(intent)
//        }else{
//            Toast.makeText(context, "Require internet connection to report", Toast.LENGTH_LONG).show()
//
//        }
//    }
//}