package com.shishkindenis.parentmodule.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shishkindenis.parentmodule.R

class MainParentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)
    }
}