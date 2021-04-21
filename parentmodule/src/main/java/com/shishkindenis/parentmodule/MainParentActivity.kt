package com.shishkindenis.parentmodule

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainParentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_login)
    }
}