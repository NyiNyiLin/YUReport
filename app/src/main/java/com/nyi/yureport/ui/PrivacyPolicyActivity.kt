package com.nyi.yureport.ui

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView
import com.nyi.yureport.R

import kotlinx.android.synthetic.main.activity_privacy_policy.*

class PrivacyPolicyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val wv : WebView = findViewById(R.id.webview_policy)
        wv.loadUrl("file:///android_asset/privacy_policy.html")
    }

}
