package com.shishkindenis.parentmodule.calendar.view

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.shishkindenis.parentmodule.R
import com.shishkindenis.parentmodule.calendar.viewModel.CalendarViewModel
import com.shishkindenis.parentmodule.databinding.ActivityCalendarBinding
import com.shishkindenis.parentmodule.maps.view.MapsActivity
import com.shishkindenis.parentmodule.singleton.DateSingleton
import dagger.android.support.DaggerAppCompatActivity
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

//class CalendarActivity : AppCompatActivity() {
class CalendarActivity : DaggerAppCompatActivity() {


//    @Inject
//    lateinit var injectionExample : InjectionExample


    companion object {
        fun getIntent(context: Context) = Intent(context, CalendarActivity::class.java)
    }

    @Inject
    lateinit var  calendarViewModel : CalendarViewModel

//    private val calendarViewModel: CalendarViewModel by viewModels()



    private val YEAR = "Year"
    private val MONTH = "Month"
    private val DAY = "Day"
    private val DATE_PATTERN = "yyyy-MM-dd"
    private var date: String? = null
    private lateinit var binding: ActivityCalendarBinding
    private lateinit var calendar: Calendar
    private var calendarYear = 0
    private var calendarMonth = 0
    private var calendarDay = 0



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        DaggerApplicationComponent
//                .builder()
//                .injectionExampleModule(InjectionExample(this))
//                .build()
//                .inject(this)

//        injectionExample.log()


        setContentView(R.layout.activity_calendar)
        binding = ActivityCalendarBinding.inflate(layoutInflater)
        val calendarActivityView: View = binding!!.root
        setContentView(calendarActivityView)
        calendar = Calendar.getInstance()
        setSupportActionBar(binding.toolbar)

        savedInstanceState?.let {
            restoreChosenDate(savedInstanceState)
        } ?: showAlertDialog()


        binding.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendarYear = year
            calendarMonth = month
            calendarDay = dayOfMonth
            calendar.let {
                it.set(calendarYear, calendarMonth, calendarDay)
            }
            val sdf = SimpleDateFormat(DATE_PATTERN)
            date = sdf.format(calendar.time)
            date?.let { Log.d("LOCATION", it) }
            DateSingleton.setDate(date.toString())
        }
        binding.btnGoToMapFromCalendar.setOnClickListener { goToMapActivity() }

        calendarViewModel.toast.observe(this, androidx.lifecycle.Observer {
            Toast.makeText(applicationContext, it, Toast.LENGTH_LONG).show()
        })

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        with(outState){
            putInt(YEAR, calendarYear)
            putInt(MONTH, calendarMonth)
            putInt(DAY, calendarDay)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        calendarViewModel.signOut()
        setResult(RESULT_OK, null)
        finish()
        return super.onOptionsItemSelected(item)
    }

    private fun showAlertDialog() {
        AlertDialog.Builder(this)
                .setMessage(R.string.choose_the_date_of_tracking)
                .setPositiveButton(R.string.ok) { dialog, which -> }
                .show()
    }

    private fun goToMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivityForResult(intent, 5)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            showToast(R.string.there_is_no_track)
        }
    }

    private fun showToast(toastMessage: Int) {
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_LONG).show()
    }

    private fun restoreChosenDate(savedInstanceState: Bundle) {
        calendarYear = savedInstanceState.getInt(YEAR)
        calendarMonth = savedInstanceState.getInt(MONTH)
        calendarDay = savedInstanceState.getInt(DAY)
        with(calendar){
            set(Calendar.YEAR, calendarYear)
            set(Calendar.MONTH, calendarMonth)
            set(Calendar.DATE, calendarDay)
        }
        val time = calendar.timeInMillis
        binding.calendarView.date = time
    }

}