package com.nyi.yureport.ui

import android.Manifest
import android.app.Dialog
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.widget.ImageView
import android.widget.Toast
import com.nyi.yureport.R
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump
import io.github.inflationx.viewpump.ViewPumpContextWrapper

open class BaseActivity : AppCompatActivity() {

    private val fontPathZawgyi = "fonts/ZawgyiOne.ttf"
    private lateinit var dia : Dialog
    private val CALL_PHONE_PERMISSION = 100

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        //unicode support
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(fontPathZawgyi)
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }

    override fun attachBaseContext(newBase: Context?) {
        if(newBase != null) super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase))
        else super.attachBaseContext(newBase)

    }

    protected fun makeUnicodeSupport(){
        //unicode support
        ViewPump.init(
            ViewPump.builder()
                .addInterceptor(
                    CalligraphyInterceptor(
                        CalligraphyConfig.Builder()
                            .setDefaultFontPath(fontPathZawgyi)
                            .setFontAttrId(R.attr.fontPath)
                            .build()
                    )
                )
                .build()
        )
    }

    fun checkPermission(){
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.CALL_PHONE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // Permission is not granted

            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CALL_PHONE),
                CALL_PHONE_PERMISSION
            )


        } else {
            //Permission granted
            //saveExcel(logVOList)
            call()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if(requestCode == CALL_PHONE_PERMISSION){
            if(grantResults.size > 0 &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED){

                call()

            }else {
                val builder = AlertDialog.Builder(this)
                builder.setMessage(
                    "ဖုန္းေခါရန္ခြင့္ျပဳခ်က္မရွိပါ။\n" +
                            "ေနာက္တစ္ႀကိမ္ႀကိဳးစားပါ။\n" +
                            "ခြင့္ျပဳခ်က္ေတာင္းသည့္အခါ Allow ကိုႏွိပ္ပါ။"
                )
                    .setCancelable(false)
                    .setPositiveButton("OK") { dialogInterface, i ->
                        checkPermission()
                    }.setNegativeButton("Cancel") {dialog, which ->

                    }

                val dia = builder.create()
                dia.show()
            }
        }else{

        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


    open fun call(){

    }
    /*fun showProgressDialog() {

        //dia.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dia = Dialog(this)
        dia.setContentView(R.layout.custom_loading_layout)
        dia.setCancelable(false)

        val gifImageView : ImageView = dia.findViewById(R.id.custom_loading_imageView)

        *//*
        it was never easy to load gif into an ImageView before Glide or Others library
        and for doing this we need DrawableImageViewTarget to that ImageView
        *//*
        val imageViewTarget = GlideDrawableImageViewTarget(gifImageView)
        //...now load that gif which we put inside the drawble folder here with the help of Glide
        Glide.with(this)
            .load(R.drawable.loading_icon)
            .placeholder(R.mipmap.ic_launcher)
            .centerCrop()
            .crossFade()
            .into(imageViewTarget)

        dia.show()

    }

    fun hideProgressDialog() {
        if(dia.isShowing) dia.dismiss()
    }*/
}