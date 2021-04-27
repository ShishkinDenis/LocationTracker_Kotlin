package com.shishkindenis.loginmodule.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.shishkindenis.loginmodule.LoginNavigation
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.databinding.ActivityPhoneAuthBinding
import com.shishkindenis.loginmodule.singletons.FirebaseUserSingleton
import com.shishkindenis.loginmodule.viewModels.PhoneAuthViewModel
import dagger.android.support.DaggerAppCompatActivity
import java.util.concurrent.TimeUnit
import javax.inject.Inject

//class PhoneAuthActivity : BaseActivity() {
class PhoneAuthActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigation: LoginNavigation

    val phoneAuthViewModel: PhoneAuthViewModel by viewModels()

    private var binding: ActivityPhoneAuthBinding? = null
//    private lateinit var moduleName: String



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        binding!!.btnRequestCode.setOnClickListener {
            binding!!.pbPhoneAuth.visibility = View.VISIBLE
            if (phoneNumberIsValid()) {
                startPhoneNumberVerification(binding!!.etPhoneNumber.text.toString())
            } else {
                setErrorIfInvalid()
            }
            binding!!.pbPhoneAuth.visibility = View.INVISIBLE
        }
        binding!!.btnVerifyCode.setOnClickListener { v ->
            binding!!.pbPhoneAuth.visibility = View.VISIBLE
            if (codeIsValid()) {
                phoneAuthViewModel.verifyPhoneNumberWithCode(
                    binding!!.etVerificationCode.text.toString()
                )
            } else {
                setErrorIfInvalid()
            }
            binding!!.pbPhoneAuth.visibility = View.INVISIBLE
        }

        phoneAuthViewModel.phoneVerificationCallback()
        phoneAuthViewModel.toast.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        })
        phoneAuthViewModel.verifyButton.observe(this, Observer {
            enableVerifyButton()
        })
        phoneAuthViewModel.phoneNumberError.observe(this, Observer {
            showInvalidPhoneNumberError()
        })

        phoneAuthViewModel.applicationModule.observe(this, Observer {
            navigation.finishLogin(this)
        })
//        moduleName = getModuleName(applicationContext).toString()
//        phoneAuthViewModel.module.observe(this, Observer {
//            if (checkModuleName(moduleName)) {
//                goToSendLocationActivity()
//            } else {
//                goToCalendarActivity()
//            }
//        })
        phoneAuthViewModel.code.observe(this, Observer {
            showInvalidCodeError()
        })
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(FirebaseUserSingleton.getFirebaseAuth())
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
            .setCallbacks(phoneAuthViewModel.phoneVerificationCallback())
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    fun phoneNumberIsValid(): Boolean {
        return binding?.etPhoneNumber?.text.toString().isNotEmpty()
    }

    fun codeIsValid(): Boolean {
        return binding?.etVerificationCode?.text.toString().isNotEmpty()
    }

    fun enableVerifyButton() {
        binding!!.btnVerifyCode.isEnabled = true
    }

    fun showInvalidPhoneNumberError() {
        binding!!.etPhoneNumber.error = getString(R.string.invalid_phone_number)
    }

    fun showInvalidCodeError() {
        binding!!.etVerificationCode.error = getString(R.string.invalid_code)
    }

    fun setErrorIfInvalid() {
        if (!phoneNumberIsValid()) {
            showInvalidPhoneNumberError()
        }
        if (!codeIsValid()) {
            showInvalidCodeError()
        }
    }
}