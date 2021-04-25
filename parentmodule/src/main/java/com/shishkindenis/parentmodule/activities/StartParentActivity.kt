package com.shishkindenis.parentmodule.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shishkindenis.loginmodule.MainLoginActivity
import com.shishkindenis.parentmodule.R

class StartParentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_parent)

        goToMainLoginActivity()
    }

    fun goToMainLoginActivity() {
        val intent = Intent(this, MainLoginActivity::class.java)
        finish()
        startActivity(intent)
    }
}