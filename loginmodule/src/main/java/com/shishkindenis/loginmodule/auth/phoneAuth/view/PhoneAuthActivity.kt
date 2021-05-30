package com.shishkindenis.loginmodule.auth.phoneAuth.view

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.auth.phoneAuth.viewModel.PhoneAuthViewModel
import com.shishkindenis.loginmodule.databinding.ActivityPhoneAuthBinding
import com.shishkindenis.loginmodule.navigation.LoginNavigation
import com.shishkindenis.loginmodule.singleton.FirebaseUserSingleton
import dagger.android.support.DaggerAppCompatActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class PhoneAuthActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigation: LoginNavigation

    @Inject
    lateinit var phoneAuthViewModel: PhoneAuthViewModel
    private lateinit var binding: ActivityPhoneAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_phone_auth)
        binding.phoneAuthActivity = this
        binding.lifecycleOwner = this
        binding.phoneAuthViewModel = phoneAuthViewModel
        phoneAuthViewModel.phoneVerificationCallback()
        observePhoneAuthViewModel()
    }

    private fun observePhoneAuthViewModel() {
        with(phoneAuthViewModel) {
            toast.observe(this@PhoneAuthActivity, Observer {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            })
            phoneNumberError.observe(this@PhoneAuthActivity, Observer {
                showInvalidPhoneNumberError()
            })
            applicationModule.observe(this@PhoneAuthActivity, Observer {
                startActivity(navigation.getPostLoginActivity(this@PhoneAuthActivity))
                finish()
            })
            code.observe(this@PhoneAuthActivity, Observer {
                showInvalidCodeError()
            })
            phoneNumber.observe(this@PhoneAuthActivity, Observer {
                startPhoneNumberVerification(it)
            })
            error.observe(this@PhoneAuthActivity, Observer {
                setErrorIfInvalid()
            })
        }
    }

    //    Cannot move to viewModel because setActivity() requires Activity
    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseUserSingleton.getFirebaseAuth())
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(phoneAuthViewModel.phoneVerificationCallback())
                .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun showInvalidPhoneNumberError() {
        binding.etPhoneNumber.error = getString(R.string.invalid_phone_number)
    }

    private fun showInvalidCodeError() {
        binding.etVerificationCode.error = getString(R.string.invalid_code)
    }

    private fun setErrorIfInvalid() {
        if (phoneAuthViewModel.phoneNumberIsValid()) {
            showInvalidPhoneNumberError()
        }
        if (phoneAuthViewModel.codeIsValid()) {
            showInvalidCodeError()
        }
    }

}