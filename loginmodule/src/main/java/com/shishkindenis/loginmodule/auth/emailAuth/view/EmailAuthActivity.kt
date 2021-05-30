package com.shishkindenis.loginmodule.auth.emailAuth.view

import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.shishkindenis.loginmodule.R
import com.shishkindenis.loginmodule.auth.emailAuth.viewModel.EmailAuthViewModel
import com.shishkindenis.loginmodule.databinding.ActivityEmailAuthBinding
import com.shishkindenis.loginmodule.navigation.LoginNavigation
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject


class EmailAuthActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigation: LoginNavigation

    @Inject
    lateinit var emailAuthViewModel: EmailAuthViewModel

    private lateinit var binding: ActivityEmailAuthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_email_auth)
        binding.emailAuthActivity = this
        binding.emailAuthViewModel = emailAuthViewModel
        observeEmailAuthViewModel()
    }

    private fun observeEmailAuthViewModel() {
        with(emailAuthViewModel) {
            toast.observe(this@EmailAuthActivity, Observer {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            })
            toastWithEmail.observe(this@EmailAuthActivity, Observer {
                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
            })
            applicationModule.observe(this@EmailAuthActivity, Observer {
                startActivity(navigation.getPostLoginActivity(this@EmailAuthActivity))
                finish()
            })
            error.observe(this@EmailAuthActivity, Observer {
                setErrorIfInvalid()
            })
        }
    }

    private fun setErrorIfInvalid() {
        if (!emailAuthViewModel.emailIsValid()) {
            binding.etEmail.error = getString(R.string.required_email)
        }
        if (!emailAuthViewModel.passwordIsValid()) {
            binding.etPassword.error = getString(R.string.required_password)
        }
    }

}