package com.shishkindenis.loginmodule.auth.phoneAuth.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
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
        binding = ActivityPhoneAuthBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

//        TODO binding внутри
        with(binding){
            btnRequestCode.setOnClickListener {
                binding.pbPhoneAuth.visibility = View.VISIBLE
                if (phoneNumberIsValid()) {
                    startPhoneNumberVerification(binding.etPhoneNumber.text.toString())
                } else {
                    setErrorIfInvalid()
                }
                binding.pbPhoneAuth.visibility = View.INVISIBLE
            }
            btnVerifyCode.setOnClickListener { v ->
                binding.pbPhoneAuth.visibility = View.VISIBLE
                if (codeIsValid()) {
                    phoneAuthViewModel.verifyPhoneNumberWithCode(
                        binding.etVerificationCode.text.toString())
                } else {
                    setErrorIfInvalid()
                }
                binding.pbPhoneAuth.visibility = View.INVISIBLE
            }
        }
//        TODO
//        binding.btnRequestCode.setOnClickListener {
//            binding.pbPhoneAuth.visibility = View.VISIBLE
//            if (phoneNumberIsValid()) {
//                startPhoneNumberVerification(binding.etPhoneNumber.text.toString())
//            } else {
//                setErrorIfInvalid()
//            }
//            binding.pbPhoneAuth.visibility = View.INVISIBLE
//        }
//        binding.btnVerifyCode.setOnClickListener { v ->
//            binding.pbPhoneAuth.visibility = View.VISIBLE
//            if (codeIsValid()) {
//                phoneAuthViewModel.verifyPhoneNumberWithCode(
//                        binding.etVerificationCode.text.toString())
//            } else {
//                setErrorIfInvalid()
//            }
//            binding.pbPhoneAuth.visibility = View.INVISIBLE
//        }
//        TODO расположение CALLBACK
        phoneAuthViewModel.phoneVerificationCallback()

//        with(phoneAuthViewModel){
//            toast.observe(this@PhoneAuthActivity, Observer {
//                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
//            })
//            verifyButton.observe(this@PhoneAuthActivity, Observer {
//                enableVerifyButton()
//            })
//            phoneNumberError.observe(this@PhoneAuthActivity, Observer {
//                showInvalidPhoneNumberError()
//            })
//            applicationModule.observe(this@PhoneAuthActivity, Observer {
//                startActivity(navigation.getPostLoginActivity(this@PhoneAuthActivity))
//                finish()
//            })
//            code.observe(this@PhoneAuthActivity, Observer {
//                showInvalidCodeError()
//            })
//        }
        observePhoneAuthViewModel()

        //        TODO
//        phoneAuthViewModel.phoneVerificationCallback()
//        phoneAuthViewModel.toast.observe(this, Observer {
//            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
//        })
//        phoneAuthViewModel.verifyButton.observe(this, Observer {
//            enableVerifyButton()
//        })
//        phoneAuthViewModel.phoneNumberError.observe(this, Observer {
//            showInvalidPhoneNumberError()
//        })
//        phoneAuthViewModel.applicationModule.observe(this, Observer {
//            startActivity(navigation.getPostLoginActivity(this))
//            finish()
//        })
//        phoneAuthViewModel.code.observe(this, Observer {
//            showInvalidCodeError()
//        })
    }

    private fun observePhoneAuthViewModel(){
        with(phoneAuthViewModel){
            toast.observe(this@PhoneAuthActivity, Observer {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            })
            verifyButton.observe(this@PhoneAuthActivity, Observer {
                enableVerifyButton()
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
        }
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

    private fun phoneNumberIsValid(): Boolean {
        return binding.etPhoneNumber.text.toString().isNotEmpty()
    }

    private fun codeIsValid(): Boolean {
        return binding.etVerificationCode.text.toString().isNotEmpty()
    }

//    TODO
    private fun enableVerifyButton() {
        binding.btnVerifyCode.isEnabled = true
    }

    private fun showInvalidPhoneNumberError() {
        binding.etPhoneNumber.error = getString(R.string.invalid_phone_number)
    }

    private fun showInvalidCodeError() {
        binding.etVerificationCode.error = getString(R.string.invalid_code)
    }

    private fun setErrorIfInvalid() {
        if (!phoneNumberIsValid()) {
            showInvalidPhoneNumberError()
        }
        if (!codeIsValid()) {
            showInvalidCodeError()
        }
    }
}