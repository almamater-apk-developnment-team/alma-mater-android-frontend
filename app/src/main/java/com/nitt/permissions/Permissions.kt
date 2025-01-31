package com.nitt.permissions

import android.Manifest
import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.pm.PackageManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class Permissions(private val activity: Activity) {

    fun createNotificationChannel() {
        val name = "Almamater Channel"
        val description = "Channel for FCM notifications"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel("1001", name, importance).apply {
            this.description = description
        }
        val notificationManager = activity.getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(channel)
    }

    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    fun requestNotificationPermissions() {
        if (ContextCompat.checkSelfPermission(
                activity,
                Manifest.permission.POST_NOTIFICATIONS,
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                activity,
                arrayOf(Manifest.permission.POST_NOTIFICATIONS),
                1,
            )
        }
    }
}
