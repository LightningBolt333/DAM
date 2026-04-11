package com.example.deviceinfo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.os.Build;
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val textBuildInfo: TextView = findViewById(R.id.textBuildInfo)
        val buildInfo = """
            Brand: ${Build.BRAND}
            Device: ${Build.DEVICE}
            Model: ${Build.MODEL}
            Manufacturer: ${Build.MANUFACTURER}
            Product: ${Build.PRODUCT}
            Hardware: ${Build.HARDWARE}
            Board: ${Build.BOARD}
            Bootloader: ${Build.BOOTLOADER}
            Display: ${Build.DISPLAY}
            Fingerprint: ${Build.FINGERPRINT}
            Host: ${Build.HOST}
            ID: ${Build.ID}
            Tags: ${Build.TAGS}
            Type: ${Build.TYPE}
            User: ${Build.USER}
            
            Android Version: ${Build.VERSION.RELEASE}
            API Level: ${Build.VERSION.SDK_INT}
        """.trimIndent()

        textBuildInfo.setText(buildInfo);

    }
}