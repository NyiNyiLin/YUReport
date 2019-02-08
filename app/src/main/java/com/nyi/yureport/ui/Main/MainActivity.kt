package com.nyi.yureport.ui.Main

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import com.nyi.yureport.R
import com.nyi.yureport.adapters.StaffAdapter
import com.nyi.yureport.adapters.StaffGridAdapter
import com.nyi.yureport.ui.BaseActivity
import com.nyi.yureport.ui.fragments.BottomSheetFragment
import com.nyi.yureport.viewfolders.StaffVH
import com.nyi.yureport.vos.StaffVO

import kotlinx.android.synthetic.main.activity_main.*
import android.support.v4.content.ContextCompat.startActivity
import android.content.Intent
import android.net.Uri
import android.widget.*
import com.nyi.yureport.ui.Report
import com.nyi.yureport.ui.fragments.InfoDialogFragment
import kotlinx.android.synthetic.main.content_main.*


class MainActivity : BaseActivity(), MainContract.MainView {

    private lateinit var menu : ImageView
    private lateinit var gridViewStaff : GridView
    private lateinit var rvStaff : RecyclerView
    private lateinit var btnCallHotLine : Button
    private lateinit var ivInfo : ImageView
    private lateinit var llReport : LinearLayout

    private var staffList : ArrayList<StaffVO> = ArrayList()
    private var hotLinNo : String? = "01535026"
    private var callNo : String? = ""
    private lateinit var mainPresenter : MainPresneter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        makeUnicodeSupport()

        menu = findViewById(R.id.iv_main_meu)
        gridViewStaff = findViewById(R.id.gridview_staff)
        rvStaff = findViewById(R.id.rv_staff_list)
        rvStaff.setLayoutManager(LinearLayoutManager(this))
        btnCallHotLine = findViewById(R.id.btn_main_call_hotline)
        ivInfo = findViewById(R.id.iv_main_info)
        llReport = findViewById(R.id.ll_main_report)

        mainPresenter = MainPresneter(this)

        mainPresenter.init()

        menu.setOnClickListener{
            // Will show the bottom sheet
            //mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
            var bottomSheetFragment = BottomSheetFragment();
            bottomSheetFragment.show(supportFragmentManager, "TAG");
        }

        gridViewStaff.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            //Toast.makeText(this, " + " + position, Toast.LENGTH_SHORT).show()

            val staff = staffList.get(position)

            //callNo = staff.phNo
            //checkPermission()

            var intent = DetailActivity.newIntent(this, staff)
            startActivity(intent)
        }

        btnCallHotLine.setOnClickListener {
            callNo = hotLinNo
            checkPermission()
        }

        ivInfo.setOnClickListener {
            val dialog = InfoDialogFragment.newInstance()
            dialog.show(supportFragmentManager, "Info Fragment")
        }

        ll_main_report.setOnClickListener {
            val intent = Report.newIntent(this)
            startActivity(intent)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showStaff(staffList: ArrayList<StaffVO>) {
        this.staffList = staffList
        val catGridAdapter = StaffGridAdapter(this, staffList)
        gridViewStaff.adapter = catGridAdapter
    }

    override fun hotLineNoAdded(phNo: String?) {
        this.hotLinNo = phNo
    }

    override fun call(){

        val intent = Intent(Intent.ACTION_CALL)

        intent.data = Uri.parse("tel:" + callNo)
        startActivity(intent)
    }
}
