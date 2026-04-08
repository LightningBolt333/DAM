package dam.A51446.coolweatherapp

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var isDaytime: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        val orientation = resources.configuration.orientation
        //aplicar tema
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            if (isDaytime) {
                setTheme(R.style.Theme_Day)
            } else {
                setTheme(R.style.Theme_Night)
            }
        } else {
            if (isDaytime) {
                setTheme(R.style.Theme_Day_Land)
            } else {
                setTheme(R.style.Theme_Night_Land)
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}