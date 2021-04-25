package com.shishkindenis.loginmodule

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shishkindenis.loginmodule.databinding.ActivityMainLoginBinding
import com.shishkindenis.loginmodule.viewModel.MainLoginViewModel


class MainLoginActivity : AppCompatActivity() {

//    @Inject
//    lateinit var firebaseUserSingleton: FirebaseUserSingleton

    val mainLoginViewModel: MainLoginViewModel by viewModels()
    private var binding: ActivityMainLoginBinding? = null
    private lateinit var appLabel: String

    override fun onCreate(savedInstanceState: Bundle?) {
//        MyApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

//        var firebaseAuth: FirebaseAuth? = firebaseUserSingleton.getFirebaseAuth();

        if (savedInstanceState == null) {
            mainLoginViewModel.checkIfUserLoggedIn()
        }

        binding!!.btnEmail.setOnClickListener { goToEmailAuthActivity() }
        binding!!.btnPhone.setOnClickListener { goToPhoneAuthActivity() }

        appLabel = getAppLable(applicationContext).toString()
        mainLoginViewModel.module.observe(this, Observer {
            if (checkModuleName()) {
                goToSendLocationActivity()
            } else {
                goToCalendarActivity()
            }
        })
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

    //    NAMING
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

    fun goToSendLocationActivity() {
        var intent: Intent? = null
        try {
            intent = Intent(
                    this,
                    Class.forName("com.shishkindenis.childmodule.activities.SendLocationActivity")
            )
            finish()
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun goToCalendarActivity() {
        var intent: Intent? = null
        try {
            intent = Intent(
                    this,
                    Class.forName("com.shishkindenis.parentmodule.activities.CalendarActivity")
            )
            finish()
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
    }

    fun checkModuleName(): Boolean {
        if (appLabel == "ChildModule") {
            return true
        }
        return false
    }


}