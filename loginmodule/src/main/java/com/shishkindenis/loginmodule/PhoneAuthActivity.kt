package com.shishkindenis.loginmodule

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.shishkindenis.loginmodule.databinding.ActivityPhoneAuthBinding
import com.shishkindenis.loginmodule.viewModel.PhoneAuthViewModel
import java.util.concurrent.TimeUnit

class PhoneAuthActivity : AppCompatActivity() {

    val phoneAuthViewModel: PhoneAuthViewModel by viewModels()
    private var binding: ActivityPhoneAuthBinding? = null
    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        //        !!знаки
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
        phoneAuthViewModel.startCalendarActivity.observe(this, Observer {
            goToChooseModuleActivity()
        })
        phoneAuthViewModel.verifyButton.observe(this, Observer {
            enableVerifyButton()
        })
        phoneAuthViewModel.phoneNumberError.observe(this, Observer {
            showInvalidPhoneNumberError()
        })
    }

    fun goToChooseModuleActivity() {
//        val intent = Intent(this, ChooseModuleActivity::class.java)
//        finish()
//        startActivity(intent)
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
//        val options = PhoneAuthOptions.newBuilder(firebaseUserSingleton!!.getFirebaseAuth()!!)
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this)
//            .setCallbacks(phoneAuthPresenter!!.phoneVerificationCallback(firebaseUserSingleton!!.getFirebaseAuth())!!)
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