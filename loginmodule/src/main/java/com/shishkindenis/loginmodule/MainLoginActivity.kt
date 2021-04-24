package com.shishkindenis.loginmodule

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shishkindenis.loginmodule.databinding.ActivityMainLoginBinding
import com.shishkindenis.loginmodule.viewModel.MainLoginViewModel


class MainLoginActivity : AppCompatActivity() {

//    @Inject
//    lateinit var firebaseUserSingleton: FirebaseUserSingleton

    val mainLoginViewModel: MainLoginViewModel by viewModels()
    private var binding: ActivityMainLoginBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
//        MyApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

//        var firebaseAuth: FirebaseAuth? = firebaseUserSingleton.getFirebaseAuth();

//            if (savedInstanceState == null) {
//                mainViewModel.checkIfUserLoggedIn()
//            }

        binding!!.btnEmail.setOnClickListener { goToEmailAuthActivity() }
        binding!!.btnPhone.setOnClickListener { goToPhoneAuthActivity() }


//        DELETE
        getAppLable(applicationContext)?.let { Log.d("LOCATION", it) }
    }

//         fun goToCalendarActivityForResult() {
//            val intent = Intent(this, CalendarActivity::class.java)
//            finish()
//            startActivityForResult(intent, 2)
//        }


    fun goToEmailAuthActivity() {
        val intent = Intent(this, EmailAuthActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun goToPhoneAuthActivity() {
        val intent = Intent(this, PhoneAuthActivity::class.java)
        finish()
        startActivity(intent)
    }

    fun getAppLable(context: Context): String? {
        val packageManager: PackageManager = context.getPackageManager()
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo =
                packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return (if (applicationInfo != null) packageManager.getApplicationLabel(applicationInfo) else "Unknown") as String
    }


}