package com.shishkindenis.childmodule.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.shishkindenis.childmodule.R
import com.shishkindenis.loginmodule.MainLoginActivity

class StartChildActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_child)
        goToMainLoginActivity()
    }

    fun goToMainLoginActivity(){
        val intent = Intent(this, MainLoginActivity::class.java)
        finish()
        startActivity(intent)
    }
}