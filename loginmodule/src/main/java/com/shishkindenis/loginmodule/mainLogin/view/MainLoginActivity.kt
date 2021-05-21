package com.shishkindenis.loginmodule.mainLogin.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.shishkindenis.loginmodule.databinding.ActivityMainLoginBinding
import com.shishkindenis.loginmodule.auth.emailAuth.view.EmailAuthActivity
import com.shishkindenis.loginmodule.mainLogin.viewModel.MainLoginViewModel
import com.shishkindenis.loginmodule.navigation.LoginNavigation
import com.shishkindenis.loginmodule.auth.phoneAuth.view.PhoneAuthActivity
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class MainLoginActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigation: LoginNavigation

    private val mainLoginViewModel: MainLoginViewModel by viewModels()

    private lateinit var binding: ActivityMainLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainLoginBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        if (savedInstanceState == null) {
            mainLoginViewModel.checkIfUserLoggedIn()
        }

        binding.btnEmail.setOnClickListener { goToEmailAuthActivity() }
        binding.btnPhone.setOnClickListener { goToPhoneAuthActivity() }

        mainLoginViewModel.applicationModule.observe(this, Observer {
            startActivity(navigation.getPostLoginActivity(this))
            finish()
        })
    }

    private fun goToEmailAuthActivity() {
        val intent = Intent(this, EmailAuthActivity::class.java)
        finish()
        startActivity(intent)
    }

    private fun goToPhoneAuthActivity() {
        val intent = Intent(this, PhoneAuthActivity::class.java)
        finish()
        startActivity(intent)
    }

}