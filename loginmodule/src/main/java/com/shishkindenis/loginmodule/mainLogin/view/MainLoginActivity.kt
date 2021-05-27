package com.shishkindenis.loginmodule.mainLogin.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.auth.emailAuth.view.EmailAuthActivity
import com.shishkindenis.loginmodule.auth.phoneAuth.view.PhoneAuthActivity

import com.shishkindenis.loginmodule.databinding.ActivityMainLoginBinding
import com.shishkindenis.loginmodule.mainLogin.viewModel.MainLoginViewModel
import com.shishkindenis.loginmodule.navigation.LoginNavigation
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainLoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigation: LoginNavigation

    @Inject
    lateinit var mainLoginViewModel: MainLoginViewModel

    private lateinit var binding: ActivityMainLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val binding: ActivityMainLoginBinding = DataBindingUtil.setContentView(this@MainLoginActivity,R.layout.activity_main_login)
//        binding.mainLoginActivity = this

        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        if (savedInstanceState == null) {
            mainLoginViewModel.checkIfUserLoggedIn()
        }
//TODO

//        binding.btnEmail.setOnClickListener { goToEmailAuthActivity() }
//        binding.btnPhone.setOnClickListener { goToPhoneAuthActivity() }

//        with(binding){
//            btnEmail.setOnClickListener { goToEmailAuthActivity() }
//            btnPhone.setOnClickListener { goToPhoneAuthActivity() }
//        }

        mainLoginViewModel.applicationModule.observe(this, Observer {
            startActivity(navigation.getPostLoginActivity(this))
            finish()
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