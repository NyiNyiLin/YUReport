package com.nyi.yureport

import android.app.Application
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nyi.yureport.vos.StaffVO


class YUReport : Application() {
    private var staffList : ArrayList<StaffVO> = ArrayList()

    private lateinit var mDatabase: DatabaseReference

    override fun onCreate() {
        super.onCreate()

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        mDatabase = FirebaseDatabase.getInstance().reference

        // Obtain the FirebaseAnalytics instance.
        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "id 123")
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "name test")
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)

        //initialUpload()
    }

    protected fun initialUpload(){

        staffList.add(StaffVO("", "ေဒၚလွေမ", "09422191372","ဝင္းမႉး", "duty", "allday"))
        staffList.add(StaffVO("", "ဦးၫြန္႔လင္း", "09402710148","ဝင္းမႉး", "duty", "allday"))
        staffList.add(StaffVO("", "ဦးလႈိင္ေအး", "09263691090","နယ္ေျမအုပ္", "duty", "allday"))
        staffList.add(StaffVO("", "ဦးေမာင္သန္း", "09250790613","နယ္ေျမအုပ္", "duty", "allday"))
        staffList.add(StaffVO("", "ဦးမ်ိဳးသန္႔", "09420150297","", "duty", "allday"))
        staffList.add(StaffVO("", "ဦးေက်ာ္ဦးေမာင္", "09400643369","", "duty", "allday"))

        staffList.add(StaffVO("", "ဦးသိန္းထိုက္", "09778499307","", "other", "allday"))
        staffList.add(StaffVO("", "ဦးသိန္းႏိုင္ ", "09423551673","", "other", "allday"))
        staffList.add(StaffVO("", "ဦးစိုးႏိုင္ ", "0931134707","", "other", "allday"))
        staffList.add(StaffVO("", "ဦးဟန္စိန္", "09420954110","", "other", "allday"))


        for(staff in staffList){
            var newKey = mDatabase.child("v1").child("staff").push().key
            if(newKey != null){
                staff.id = newKey
                mDatabase.child("v1").child("staff").child(newKey).setValue(staff)
            }
        }

    }
}