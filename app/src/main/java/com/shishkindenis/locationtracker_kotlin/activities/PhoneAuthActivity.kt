package com.shishkindenis.locationtracker_kotlin.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shishkindenis.locationtracker_kotlin.R
import com.shishkindenis.locationtracker_kotlin.databinding.ActivityPhoneAuthBinding
import com.shishkindenis.locationtracker_kotlin.viewModel.PhoneAuthViewModel

class PhoneAuthActivity : AppCompatActivity() {

    val phoneAuthViewModel : PhoneAuthViewModel by viewModels()
    private var binding: ActivityPhoneAuthBinding? = null
//    РЕГИСТРАЦИЯ В Firebase
//    private val auth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
//        MyApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        //        !!знаки
        val view: View = binding!!.root
        setContentView(view)

        binding!!.btnRequestCode.setOnClickListener {
            binding!!.pbPhoneAuth.visibility = View.VISIBLE
            if (phoneNumberIsValid()) {
//                startPhoneNumberVerification(binding!!.etPhoneNumber.text.toString())
            } else {
                setErrorIfInvalid()
            }
            binding!!.pbPhoneAuth.visibility = View.INVISIBLE
        }
        binding!!.btnVerifyCode.setOnClickListener { v ->
            binding!!.pbPhoneAuth.visibility = View.VISIBLE
            if (codeIsValid()) {
                phoneAuthViewModel.verifyPhoneNumberWithCode(
              binding!!.etVerificationCode.text.toString())
            } else {
                setErrorIfInvalid()
            }
            binding!!.pbPhoneAuth.visibility = View.INVISIBLE
        }
        phoneAuthViewModel.phoneVerificationCallback()
    }

    //ИЛИ SendLocationActivity
//    fun goToCalendarActivity() {
//        val intent = Intent(this, CalendarActivity::class.java)
//        finish()
//        startActivity(intent)
//    }

//    private fun startPhoneNumberVerification(phoneNumber: String) {
////        val options = PhoneAuthOptions.newBuilder(firebaseUserSingleton!!.getFirebaseAuth()!!)
//        val options = PhoneAuthOptions.newBuilder(auth)
//            .setPhoneNumber(phoneNumber)
//            .setTimeout(60L, TimeUnit.SECONDS)
//            .setActivity(this)
////            .setCallbacks(phoneAuthPresenter!!.phoneVerificationCallback(firebaseUserSingleton!!.getFirebaseAuth())!!)
//            .setCallbacks(phoneAuthViewModel.phoneVerificationCallback())
//            .build()
//        PhoneAuthProvider.verifyPhoneNumber(options)
//    }

    fun phoneNumberIsValid(): Boolean {
        return !binding!!.etPhoneNumber.text.toString().isEmpty()
    }

    fun codeIsValid(): Boolean {
        return !binding!!.etVerificationCode.text.toString().isEmpty()
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