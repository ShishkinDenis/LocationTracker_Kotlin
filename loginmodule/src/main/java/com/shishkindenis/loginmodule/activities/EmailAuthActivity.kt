package com.shishkindenis.loginmodule.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.databinding.ActivityEmailAuthBinding
import com.shishkindenis.loginmodule.viewModels.EmailAuthViewModel

class EmailAuthActivity : BaseActivity() {

    val emailAuthViewModel: EmailAuthViewModel by viewModels()

    private var binding: ActivityEmailAuthBinding? = null
    private lateinit var moduleName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailAuthBinding.inflate(layoutInflater)
        val view: View = binding!!.root
        setContentView(view)

        binding!!.btnRegister.setOnClickListener {
            if (emailIsValid() and passwordIsValid()) {
                registerIfValid()
            } else {
                setErrorIfInvalid()
            }
        }
        binding!!.btnLogin.setOnClickListener {
            if (emailIsValid() and passwordIsValid()) {
                logInIfValid()
            } else {
                setErrorIfInvalid()
            }
        }
        emailAuthViewModel.toast.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        })
        emailAuthViewModel.toastwithEmail.observe(this, Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        })

        moduleName = getModuleName(applicationContext).toString()
        emailAuthViewModel.module.observe(this, Observer {
            if (checkModuleName(moduleName)) {
                goToSendLocationActivity()
            } else {
                goToCalendarActivity()
            }
        })
    }

    fun emailIsValid(): Boolean {
        return binding?.etEmail?.text.toString().isNotEmpty()
    }

    fun passwordIsValid(): Boolean {
        return binding?.etPassword?.text.toString().isNotEmpty()
    }

    fun setErrorIfInvalid() {
        if (!emailIsValid()) {
            binding?.etEmail?.error = getString(R.string.required_email)
        }
        if (!passwordIsValid()) {
            binding?.etPassword?.error = getString(R.string.required_password)
        }
    }

    fun logInIfValid() {
        binding!!.pbEmailAuth.visibility = View.VISIBLE
        emailAuthViewModel.signIn(
            binding!!.etEmail.text.toString(),
            binding!!.etPassword.text.toString()
        )
        binding!!.pbEmailAuth.visibility = View.INVISIBLE
    }

    fun registerIfValid() {
        binding!!.pbEmailAuth.visibility = View.VISIBLE
        emailAuthViewModel.createAccount(
            binding!!.etEmail.text.toString(),
            binding!!.etPassword.text.toString()
        )
        binding!!.pbEmailAuth.visibility = View.INVISIBLE
    }

}