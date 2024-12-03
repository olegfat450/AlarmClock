package com.example.alarmclock

import android.annotation.SuppressLint
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlin.system.exitProcess

class Activity2 : AppCompatActivity() {

    var ringtone: Ringtone? = null

     private lateinit var imageTv: ImageView

    @SuppressLint("MissingInflatedId", "SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_2)

        imageTv = findViewById(R.id.imageTv)



        imageTv.setImageResource(R.drawable.build)

        var notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
         ringtone = RingtoneManager.getRingtone(this,notificationUri)

        if ( ringtone == null ) { notificationUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE); ringtone = RingtoneManager.getRingtone(this,notificationUri)}

         if ( ringtone != null ) ringtone!!.play()
                   imageTv.setOnClickListener { ringtone!!.stop(); finish() }

    }
}