package com.shishkindenis.parentmodule

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class StartParentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_parent)

        val intent = Intent(this, MainParentActivity::class.java)
        finish()
        startActivity(intent)
    }
}