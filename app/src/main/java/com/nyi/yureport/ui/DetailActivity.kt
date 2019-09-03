package com.nyi.yureport.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.google.firebase.analytics.FirebaseAnalytics
import com.nyi.yureport.R
import com.nyi.yureport.vos.StaffVO
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : BaseActivity() {

    var staffVO : StaffVO = StaffVO()

    private lateinit var tvPosition : TextView
    private lateinit var tvPhNo : TextView
    private lateinit var tvDutyDay : TextView
    private lateinit var tvDutyTime : TextView

    private lateinit var firebaseAnalytics: FirebaseAnalytics

    companion object {

        val ARG_STAFF_ID = "staff_id"

        fun newIntent(context : Context, staffVO : StaffVO) : Intent{
            var intent = Intent(context, DetailActivity::class.java)
            intent = StaffVO.convertObjectToIntent(intent, staffVO)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        tvPosition = findViewById(R.id.tv_detail_position)
        tvPhNo = findViewById(R.id.tv_detail_phno)
        tvDutyDay = findViewById(R.id.tv_detail_duty_day)
        tvDutyTime = findViewById(R.id.tv_detail_duty_time)

        makeUnicodeSupport()
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        staffVO = StaffVO.convertIntentToObject(intent)
        supportActionBar?.title = staffVO.name
        tvPhNo.text = staffVO.phNo
        tvPosition.text = staffVO.position
        tvDutyDay.text = staffVO.dutyDay
        tvDutyTime.text = staffVO.dutyTime


        fab.setOnClickListener { view ->
            /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()*/

            checkPermission()
        }

        // Obtain the FirebaseAnalytics instance.
        val firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        val bundle = Bundle()
        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, staffVO.id)
        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, staffVO.phNo)
        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "image")
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle)
    }

    override fun call() {
        super.call()
        val intent = Intent(Intent.ACTION_CALL)

        intent.data = Uri.parse("tel:" + staffVO.phNo)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == android.R.id.home){
            finish()
        }

        return super.onOptionsItemSelected(item)
    }
}
