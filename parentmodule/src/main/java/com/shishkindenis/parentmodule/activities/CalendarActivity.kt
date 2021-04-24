package com.shishkindenis.parentmodule.activities

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.shishkindenis.parentmodule.MapsActivity
import com.shishkindenis.parentmodule.R
import com.shishkindenis.parentmodule.databinding.ActivityCalendarBinding
import com.shishkindenis.parentmodule.singletons.DateSingleton
import com.shishkindenis.parentmodule.viewModels.CalendarViewModel
import java.text.SimpleDateFormat
import java.util.*

class CalendarActivity : AppCompatActivity() {
    val YEAR = "Year"
    val MONTH = "Month"
    val DAY = "Day"
    val DATE_PATTERN = "yyyy-MM-dd"

//    @Inject
//    @InjectPresenter
//    internal lateinit var calendarPresenter: CalendarPresenter

    var date : String? = null
//    @Inject
//    lateinit var dateSingleton: DateSingleton
//    ПОМЕНЯТЬ НА Inject
     var dateSingleton: DateSingleton = DateSingleton()

    var binding: ActivityCalendarBinding? = null
    var calendar: Calendar? = null
    var calendarYear = 0
    var calendarMonth = 0
    var calendarDay = 0

    val calendarViewModel: CalendarViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
//        MyApplication.appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        binding = ActivityCalendarBinding.inflate(layoutInflater)
        val calendarActivityView: View = binding!!.root
        setContentView(calendarActivityView)
        calendar = Calendar.getInstance()
        setSupportActionBar(binding!!.toolbar)

        if (savedInstanceState == null) {
            showAlertDialog()
        }

        if(savedInstanceState != null){
            restoreChosenDate(savedInstanceState)
        }

        binding!!.calendarView.setOnDateChangeListener { view, year, month, dayOfMonth ->
            calendarYear = year
            calendarMonth = month
            calendarDay = dayOfMonth
//          calendar.set(calendarYear, calendarMonth, calendarDay)
            calendar.let {
                if (it != null) {
                    it.set(calendarYear, calendarMonth, calendarDay)
                }
            }
            val sdf = SimpleDateFormat(DATE_PATTERN).apply {
                //          date = sdf.format(calendar.getTime())
                date = format(calendar.getTime())
            }
//          dateSingleton.setDate(date)
            date?.let { dateSingleton.setDate(it) }
        }
        binding!!.btnGoToMapFromCalendar.setOnClickListener { goToMapActivity() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(YEAR, calendarYear)
        outState.putInt(MONTH, calendarMonth)
        outState.putInt(DAY, calendarDay)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        calendarPresenter?.signOut()
        calendarViewModel.signOut()
        setResult(RESULT_OK, null)
        finish()
        return super.onOptionsItemSelected(item)
    }

    fun showAlertDialog() {
        AlertDialog.Builder(this)
            .setMessage(R.string.choose_the_date_of_tracking)
            .setPositiveButton(R.string.ok) { dialog, which -> }
            .show()
    }

    fun goToMapActivity() {
        val intent = Intent(this, MapsActivity::class.java)
        startActivityForResult(intent, 5)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_CANCELED) {
            showToast(R.string.there_is_no_track)
        }
    }

    fun showToast(toastMessage: Int) {
        Toast.makeText(applicationContext, toastMessage, Toast.LENGTH_LONG).show()
    }

    fun restoreChosenDate(savedInstanceState: Bundle) {
        calendarYear = savedInstanceState.getInt(YEAR)
        calendarMonth = savedInstanceState.getInt(MONTH)
        calendarDay = savedInstanceState.getInt(DAY)
        calendar!!.set(Calendar.YEAR,calendarYear)
        calendar!!.set(Calendar.MONTH,calendarMonth)
        calendar!!.set(Calendar.DATE,calendarDay)
        val time = calendar!!.timeInMillis
        binding?.calendarView?.date = time
    }

}