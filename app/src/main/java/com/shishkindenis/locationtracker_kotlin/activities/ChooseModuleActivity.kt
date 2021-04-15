package com.shishkindenis.locationtracker_kotlin.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.shishkindenis.locationtracker_kotlin.databinding.ActivityChooseModuleBinding

class ChooseModuleActivity : AppCompatActivity() {
    private var binding: ActivityChooseModuleBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChooseModuleBinding.inflate(layoutInflater)
//        !!знаки
        val view: View = binding!!.root
        setContentView(view)

        binding!!.btnChild.setOnClickListener {
//            goToSendLocationActivity()
        }

        binding!!.btnParent.setOnClickListener {
//            goToCalendarActivity()
        }

    }

//    fun goToCalendarActivity() {
//        val intent = Intent(this, CalendarActivity::class.java)
//        finish()
//        startActivity(intent)
//    }

//    fun goToSendLocationActivity() {
//        val intent = Intent(this, SendLocationActivity::class.java)
//        finish()
//        startActivity(intent)
//    }
}