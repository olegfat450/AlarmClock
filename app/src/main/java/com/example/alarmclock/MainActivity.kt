package com.example.alarmclock

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.AlarmManager.RTC_WAKEUP
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.icu.util.Calendar
import android.icu.util.LocaleData
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar


import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.AlarmManagerCompat
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.text.SimpleDateFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var switch1: Switch
    private lateinit var switch2: Switch
    private lateinit var switch3: Switch
    private lateinit var textClock1: TextView
    private lateinit var textClock2: TextView
    private lateinit var textClock3: TextView

    private lateinit var toolbar: Toolbar

    private var calendars: Array<Calendar?> = arrayOf(null, null, null)

    //   Calendar? = null
    private var materialTimePicker: MaterialTimePicker? = null
    val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    private lateinit var textClock: Array<TextView>
    private lateinit var switch: Array<Switch>
    var s = 0

    var alarmManager: MutableList<AlarmManager?> = mutableListOf(null, null, null)


    @SuppressLint("ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)



        switch1 = findViewById(R.id.switch1)
        switch2 = findViewById(R.id.switch2)
        switch3 = findViewById(R.id.switch3)
        textClock1 = findViewById(R.id.textClock1)
        textClock2 = findViewById(R.id.textClock2)
        textClock3 = findViewById(R.id.textClock3)

        textClock = arrayOf(textClock1, textClock2, textClock3)
        switch = arrayOf(switch1, switch2, switch3)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        title = "   БУДИЛЬНИК"
    }

    fun onClickSwitch(v: View) {
        s = when (v.id) {
            R.id.switch1 -> 0
            R.id.switch2 -> 1
            R.id.switch3 -> 2
            else -> 0
        }
        if (v is Switch) {
            if (v.isChecked) {
                textClock[s].setTextColor(Color.BLACK); if (calendars[s] != null) setAlarmOn(
                    calendars[s]
                )
            } else {
                textClock[s].setTextColor(getColor(R.color.white1));if (calendars[s] != null) setAlarmOff()
            }
        }
    }

    fun onClick(v: View) {
        s = when (v.id) {
            R.id.textClock1 -> 0
            R.id.textClock2 -> 1
            R.id.textClock3 -> 2
            else -> 0
        }

        var calendar: Calendar? = null

        materialTimePicker = MaterialTimePicker.Builder()
            .setTimeFormat(TimeFormat.CLOCK_24H)
            .setHour(12)
            .setMinute(0)
            .build()
        materialTimePicker!!.addOnPositiveButtonClickListener {
            calendar = Calendar.getInstance()
            calendar?.set(Calendar.SECOND, 0)
            calendar?.set(Calendar.MILLISECOND, 0)
            calendar?.set(Calendar.MINUTE, materialTimePicker!!.minute)
            calendar?.set(Calendar.HOUR_OF_DAY, materialTimePicker!!.hour)

            calendars[s] = calendar
            textClock[s].setTextColor(Color.BLACK)
            textClock[s].setText(dateFormat.format(calendars[s]!!.time)); switch[s].isChecked = true
            setAlarmOn(calendars[s])
        }


        materialTimePicker!!.show(supportFragmentManager, "tag_picker")
    }

    fun setAlarmOn(calendar1: Calendar?) {

        alarmManager[s] = getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager[s]?.setExact(RTC_WAKEUP, calendar1?.timeInMillis!!, getAlarmPendingIntent()!!)

        val cal = calendar1?.timeInMillis?.minus((Calendar.getInstance(Locale.getDefault()).timeInMillis))

        Toast.makeText(
            this,
            "Будильник сработает через: ${dateFormat.format(cal)}",
            Toast.LENGTH_LONG
        ).show()
    }

    fun setAlarmOff() {
        alarmManager[s]?.cancel(getAlarmPendingIntent()!!)
        Toast.makeText(
            this,
            "Будильник${dateFormat.format(calendars[s]?.time)} выключен",
            Toast.LENGTH_LONG
        ).show()
    }


    private fun getAlarmPendingIntent(): PendingIntent? {
        val intent = Intent(this, AlarmReceiver::class.java)
        intent.setAction("alarmWakeUp" + calendars[s]?.timeInMillis)

        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
        return PendingIntent.getBroadcast(
            this,
            1,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return super.onCreateOptionsMenu(menu) }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finishAffinity()
        return super.onOptionsItemSelected(item) }

}





















