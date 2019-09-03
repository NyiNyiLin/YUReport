package com.nyi.yureport.ui.Main

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.nyi.yureport.R
import com.nyi.yureport.adapters.StaffGridAdapter
import com.nyi.yureport.ui.BaseActivity
import com.nyi.yureport.vos.StaffVO

import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import android.util.Log
import android.widget.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.nyi.yureport.ui.DetailActivity
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
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    var prefs: SharedPreferences? = null
    private var firstTime = 0
    private val ARG_IS_FIRST_TIME = "first_time"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setSupportActionBar(toolbar)

        makeUnicodeSupport()

        menu = findViewById(R.id.iv_main_meu)
        gridViewStaff = findViewById(R.id.gridview_staff)
        rvStaff = findViewById(R.id.rv_staff_list)
        rvStaff.setLayoutManager(LinearLayoutManager(this))
        btnCallHotLine = findViewById(R.id.btn_main_call_hotline)
        ivInfo = findViewById(R.id.iv_main_info)
        llReport = findViewById(R.id.ll_main_report)

        firebaseAnalytics = FirebaseAnalytics.getInstance(this)

        mainPresenter = MainPresneter(this)

        mainPresenter.init()

        checkFirstTime()

        menu.setOnClickListener{
            // Will show the bottom sheet
            //mBottomSheetBehaviour.setState(BottomSheetBehavior.STATE_EXPANDED);
            //var bottomSheetFragment = BottomSheetFragment();
            //bottomSheetFragment.show(supportFragmentManager, "TAG");
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
        listenDynamicLink()

        createDynamicLink()

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
        //test crash
        //Crashlytics.getInstance().crash()

        val intent = Intent(Intent.ACTION_CALL)

        intent.data = Uri.parse("tel:" + callNo)
        startActivity(intent)
    }

    fun checkFirstTime(){
        prefs = this.getSharedPreferences("com.nyi.yureport", Context.MODE_PRIVATE)
        val isFirstTime = prefs!!.getInt(ARG_IS_FIRST_TIME, firstTime)

        if(isFirstTime == firstTime){
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
            val isConnected :Boolean = activeNetwork?.isConnected == true
            if(isConnected){

                val editor = prefs!!.edit()
                editor.putInt(ARG_IS_FIRST_TIME, 1)
                editor.apply()

            }else{
                Toast.makeText(this, "Require internet connection to load data for the first time", Toast.LENGTH_LONG).show()

            }
        }

    }

    fun createDynamicLink(){

        val dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(Uri.parse("https://www.example.com/refer?data=a"))
            .setDomainUriPrefix("https://yureport.page.link")
            // Open links with this app on Android
            .setAndroidParameters(DynamicLink.AndroidParameters.Builder().build())
            .buildShortDynamicLink()
            .addOnSuccessListener { result ->
                // Short link created
                val shortLink = result.shortLink
                val flowchartLink = result.previewLink
                Log.i("yureport", "shortLink " + shortLink + " flowchartLink " + flowchartLink)
            }.addOnFailureListener {
                // Error
                // ...
                Log.e("yureport", it.message)
            }
    }

    fun listenDynamicLink(){
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(this) { pendingDynamicLinkData ->
                // Get deep link from result (may be null if no link is found)
                var deepLink: Uri? = null
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link
                    Log.i("yureport", "deeplink " + deepLink)
                    val paramater = deepLink!!.getQueryParameter("data")
                    Log.i("yureport", "paramater " + paramater)
                }

                Log.i("yureport", "deeplink null" )
                // Handle the deep link. For example, open the linked
                // content, or apply promotional credit to the user's
                // account.
                // ...

                // ...
            }
            .addOnFailureListener(this) { e -> Log.w("yureport", "getDynamicLink:onFailure", e) }
    }
}
