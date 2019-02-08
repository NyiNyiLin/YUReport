package com.nyi.yureport.vos

import android.content.Intent

class StaffVO(var id : String,
              var name : String,
              var phNo : String,
              var position : String,
              var dutyDay : String,
              var dutyTime : String) {

    constructor() : this("", "", "",  "", "", "")

    companion object {
        val ARG_ID = "id"
        val ARG_NAME = "name"
        val ARG_PHNO = "phno"
        val ARG_POSITION = "position"
        val ARG_DUTY_DAY = "duty_day"
        val ARG_DUTY_TIME = "duty_time"

        fun convertObjectToIntent(intent : Intent, staffVO: StaffVO) : Intent{
            intent.putExtra(ARG_ID, staffVO.id)
            intent.putExtra(ARG_NAME, staffVO.name)
            intent.putExtra(ARG_PHNO, staffVO.phNo)
            intent.putExtra(ARG_POSITION, staffVO.position)
            intent.putExtra(ARG_DUTY_DAY, staffVO.dutyDay)
            intent.putExtra(ARG_DUTY_TIME, staffVO.dutyTime)

            return intent
        }

        fun convertIntentToObject(intent : Intent) : StaffVO{
            val staffVO = StaffVO()

            staffVO.id = intent.getStringExtra(ARG_ID)
            staffVO.name = intent.getStringExtra(ARG_NAME)
            staffVO.phNo = intent.getStringExtra(ARG_PHNO)
            staffVO.position = intent.getStringExtra(ARG_POSITION)
            staffVO.dutyDay = intent.getStringExtra(ARG_DUTY_DAY)
            staffVO.dutyTime = intent.getStringExtra(ARG_DUTY_TIME)

            return staffVO
        }
    }
}