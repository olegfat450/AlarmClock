package com.example.alarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager

class AlarmReceiver: BroadcastReceiver() {

     var ringtone: Ringtone? = null

    override fun onReceive(context: Context?, intent: Intent?) {

               val intent = Intent(context!!.applicationContext,Activity2::class.java)
                   intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

                   context.startActivity(intent)
    }

}