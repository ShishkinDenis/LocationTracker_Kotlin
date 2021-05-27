package com.shishkindenis.loginmodule.auth.emailAuth.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
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

        binding = ActivityEmailAuthBinding.inflate(layoutInflater)
        val view: View = binding.root
        setContentView(view)

//        TODO binding with
        with(binding){
            btnRegister.setOnClickListener {
                if (emailIsValid() and passwordIsValid()) {
                    registerIfValid()
                } else {
                    setErrorIfInvalid()
                }
            }
            btnLogin.setOnClickListener {
                if (emailIsValid() and passwordIsValid()) {
                    logInIfValid()
                } else {
                    setErrorIfInvalid()
                }
            }
        }
//        binding.btnRegister.setOnClickListener {
//            if (emailIsValid() and passwordIsValid()) {
//                registerIfValid()
//            } else {
//                setErrorIfInvalid()
//            }
//        }
//        binding.btnLogin.setOnClickListener {
//            if (emailIsValid() and passwordIsValid()) {
//                logInIfValid()
//            } else {
//                setErrorIfInvalid()
//            }
//        }


//        TODO DELETE
//        emailAuthViewModel.toast.observe(this, Observer {
//            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
//        })
//        emailAuthViewModel.toastWithEmail.observe(this, Observer {
//            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
//        })
//        emailAuthViewModel.applicationModule.observe(this, Observer {
//            startActivity(navigation.getPostLoginActivity(this))
//            finish()
//        })

//        with(emailAuthViewModel) {
//            toast.observe(this@EmailAuthActivity, Observer {
//                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
//            })
//            toastWithEmail.observe(this@EmailAuthActivity, Observer {
//                Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
//            })
//            applicationModule.observe(this@EmailAuthActivity, Observer {
//                startActivity(navigation.getPostLoginActivity(this@EmailAuthActivity))
//                finish()
//            })
//        }
        observeEmailAuthViewModel()
    }

    private fun observeEmailAuthViewModel(){
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
        }
    }

    private fun emailIsValid(): Boolean {
        return binding.etEmail.text.toString().isNotEmpty()
    }

    private fun passwordIsValid(): Boolean {
        return binding.etPassword.text.toString().isNotEmpty()
    }

    private fun setErrorIfInvalid() {
        if (!emailIsValid()) {
            binding.etEmail.error = getString(R.string.required_email)
        }
        if (!passwordIsValid()) {
            binding.etPassword.error = getString(R.string.required_password)
        }
    }

    private fun logInIfValid() {
        binding.pbEmailAuth.visibility = View.VISIBLE
        emailAuthViewModel.signIn(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        binding.pbEmailAuth.visibility = View.INVISIBLE
    }

    private fun registerIfValid() {
        binding.pbEmailAuth.visibility = View.VISIBLE
        emailAuthViewModel.createAccount(binding.etEmail.text.toString(), binding.etPassword.text.toString())
        binding.pbEmailAuth.visibility = View.INVISIBLE
    }

}