package com.shishkindenis.locationtracker_kotlin.activities

//class MainActivity : AppCompatActivity() {
//
////    @Inject
////    lateinit var firebaseUserSingleton: FirebaseUserSingleton
//
//    val mainViewModel: MainViewModel by viewModels()
////    private var binding: ActivityMainBinding? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        MyApplication.appComponent.inject(this)
//        super.onCreate(savedInstanceState)
////        binding = ActivityMainBinding.inflate(layoutInflater)
////        val view: View = binding!!.root
////        setContentView(view)
//
////        var firebaseAuth: FirebaseAuth? = firebaseUserSingleton.getFirebaseAuth();
//
////            if (savedInstanceState == null) {
////                mainViewModel.checkIfUserLoggedIn()
////            }
//
////        binding!!.btnEmail.setOnClickListener { goToEmailAuthActivity() }
////        binding!!.btnPhone.setOnClickListener { goToPhoneAuthActivity() }
//
////            DELETE
//        mainViewModel.logSomething()
//
////                mainViewModel.toast.observe(this, Observer {
////                    Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
////                })
////            mainViewModel.doSomething()
//
//    }
//
////         fun goToCalendarActivityForResult() {
////            val intent = Intent(this, CalendarActivity::class.java)
////            finish()
////            startActivityForResult(intent, 2)
////        }
//
//
//    fun goToEmailAuthActivity() {
//        val intent = Intent(this, EmailAuthActivity::class.java)
//        finish()
//        startActivity(intent)
//    }
//
//    fun goToPhoneAuthActivity() {
//        val intent = Intent(this, PhoneAuthActivity::class.java)
//        finish()
//        startActivity(intent)
//    }
//
//
//}