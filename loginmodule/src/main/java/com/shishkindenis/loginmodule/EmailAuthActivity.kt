package com.shishkindenis.loginmodule

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
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

    private lateinit var appLabel : String

     fun checkModuleName(): Boolean {
        if(appLabel == "ChildModule") {
            return true
        }
       return false
    }

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

//        DELETE
//        emailAuthViewModel.startSendLocationActivity.observe(this, Observer {
//            goToSendLocationActivity()
//        })

        appLabel = getAppLable(applicationContext).toString()
        emailAuthViewModel.module.observe(this, Observer {
            if(checkModuleName()) {
                goToSendLocationActivity()
            }
            else{
                goToCalendarActivity()
            }
        })
//        DELETE
//        if(checkModuleName()) {
//            Log.d("LOCATION", checkModuleName().toString())
//        }

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

    fun goToCalendarActivity() {
        var intent: Intent? = null
        try {
            intent = Intent(
                this,
                Class.forName("com.shishkindenis.parentmodule.activities.CalendarActivity")
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

    fun getAppLable(context: Context): String? {
        val packageManager: PackageManager = context.getPackageManager()
        var applicationInfo: ApplicationInfo? = null
        try {
            applicationInfo =
                packageManager.getApplicationInfo(context.getApplicationInfo().packageName, 0)
        } catch (e: PackageManager.NameNotFoundException) {
        }
        return (if (applicationInfo != null) packageManager.getApplicationLabel(applicationInfo) else "Unknown") as String
    }


}