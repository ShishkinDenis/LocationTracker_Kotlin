package com.shishkindenis.loginmodule

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.shishkindenis.loginmodule.databinding.ActivityEmailAuthBinding
import com.shishkindenis.loginmodule.viewModel.EmailAuthViewModel

class EmailAuthActivity : AppCompatActivity() {

    val emailAuthViewModel: EmailAuthViewModel by viewModels()
    private var binding: ActivityEmailAuthBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailAuthBinding.inflate(layoutInflater)
//        !!знаки
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
        emailAuthViewModel.startSendLocationActivity.observe(this, Observer {
            goToSendLocationActivity()
        })
    }

    fun goToSendLocationActivity() {
        var intent: Intent? = null
        try {
            intent = Intent(
                    this,
                    Class.forName("com.shishkindenis.childmodule.activities.SendLocationActivity")
            )
            finish()
            startActivity(intent)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        }
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