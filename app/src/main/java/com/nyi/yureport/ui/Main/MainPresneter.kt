package com.nyi.yureport.ui.Main

import android.util.Log
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.nyi.yureport.vos.StaffVO
import com.google.firebase.database.ValueEventListener
import com.nyi.yureport.vos.PhNoVO
import com.nyi.yureport.vos.ShopVO




class MainPresneter(val mainView : MainContract.MainView) : MainContract.MainPresneter{

    private var staffList : ArrayList<StaffVO> = ArrayList()

    override fun init() {
        loadAllStaff()
        loadHotLinePh()
        //mainView.showStaff(staffList)
        //val type = ArrayList<String>()
        //type.add("Breakfast")
        //type.add("Lunch")
        //type.add("Drink")
        //val shopVO1 = ShopVO("Shan Ma Lay", "Shan Ma Lay", "link", "canteen", type)
        //FirebaseDatabase.getInstance().getReference().child("v1").child("test").child("Shan Ma Lay").setValue(shopVO1)

    }

    private fun loadHotLinePh(){
        val list = object : ValueEventListener{
            override fun onDataChange(p0: DataSnapshot) {
                val phNo = p0.getValue(String::class.java)
                Log.d("global", phNo)
                mainView.hotLineNoAdded(phNo)
            }

            override fun onCancelled(p0: DatabaseError) {

            }
        }
        FirebaseDatabase.getInstance().getReference().child("v1").child("phno").addValueEventListener(list)

    }

    private fun loadAllStaff(){
        var staffList : ArrayList<StaffVO> = ArrayList()

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {

                val staffVO = dataSnapshot.getValue(StaffVO::class.java)

                if(staffVO != null) {
                    staffList.add(staffVO)
                    mainView.showStaff(staffList)
                }
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val newStaff = dataSnapshot.getValue(StaffVO::class.java)

                if(newStaff != null) {
                    var index = 0

                    for(oldStaff in staffList){
                        if(oldStaff.id.equals(newStaff.id)){
                            staffList.removeAt(index)
                            staffList.add(index, newStaff)
                            mainView.showStaff(staffList)
                            break
                        }
                        index ++
                    }
                }
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val deletedStaff = dataSnapshot.getValue(StaffVO::class.java)


                if(deletedStaff != null) {
                    var index = 0

                    for(staff in staffList){
                        if(staff.id.equals(deletedStaff.id)){
                            staffList.removeAt(index)
                            mainView.showStaff(staffList)
                            break
                        }
                        index ++
                    }
                }
            }

            override fun onChildMoved(dataSnapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(databaseError: DatabaseError) {

            }
        }

        FirebaseDatabase.getInstance().getReference().child("v1").child("staff").addChildEventListener(childEventListener)
    }
}
