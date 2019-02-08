package com.nyi.yureport.ui.Main

import com.nyi.yureport.vos.StaffVO

interface MainContract {

    interface MainPresneter{
        fun init()
    }

    interface MainView{
        fun showStaff(staffList : ArrayList<StaffVO>)
        fun hotLineNoAdded(phNo : String?)
    }
}