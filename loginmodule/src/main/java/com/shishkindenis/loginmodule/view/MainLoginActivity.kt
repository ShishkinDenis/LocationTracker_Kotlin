package com.shishkindenis.loginmodule.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.shishkindenis.loginmodule.LoginNavigation
import com.shishkindenis.loginmodule.databinding.ActivityMainLoginBinding
import com.shishkindenis.loginmodule.emailAuth.view.EmailAuthActivity
import com.shishkindenis.loginmodule.viewModels.MainLoginViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


//class MainLoginActivity : BaseActivity() {
class MainLoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigation: LoginNavigation

    val mainLoginViewModel: MainLoginViewModel by viewModels()

    private var binding: ActivityMainLoginBinding? = null
    private lateinit var moduleName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        if (savedInstanceState == null) {
            mainLoginViewModel.checkIfUserLoggedIn()
        }

        binding!!.btnEmail.setOnClickListener { goToEmailAuthActivity() }
        binding!!.btnPhone.setOnClickListener { goToPhoneAuthActivity() }

//        moduleName = getModuleName(applicationContext).toString()
//        mainLoginViewModel.module.observe(this, Observer {
//            if (checkModuleName(moduleName)) {
//                goToSendLocationActivity()
//            } else {
//                goToCalendarActivity()
//            }
//        })

        mainLoginViewModel.applicationModule.observe(this, Observer {
            navigation.finishLogin(this)
        })
    }

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

}