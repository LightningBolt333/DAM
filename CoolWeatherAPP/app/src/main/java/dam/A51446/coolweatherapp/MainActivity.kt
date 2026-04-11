package dam.A51446.coolweatherapp

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.gson.Gson
import java.io.InputStreamReader
import java.net.URL

class MainActivity : AppCompatActivity() {
    private var isDaytime: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        //como o recerate apaga variaveis temporarias, pode reiniciar
        //portanto verificar estado antes de criar a view
        if (savedInstanceState != null) {
            isDaytime = savedInstanceState.getBoolean("isDaytime", false)

            val savedJson = savedInstanceState.getString("lastWeatherData")
            if (savedJson != null) {
                val restoredData = Gson().fromJson(savedJson, WeatherData::class.java)
                window.decorView.post { updateUI(restoredData) }
            }
        }

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
        setContentView(R.layout.activity_main)

        val btnUpdate = findViewById<Button>(R.id.button)
        val editLat = findViewById<EditText>(R.id.editLatitude)
        val editLong = findViewById<EditText>(R.id.editLongitude)

        btnUpdate.setOnClickListener {
            val lat = editLat.text.toString()
            val lon = editLong.text.toString()

            if (lat.isNotEmpty() && lon.isNotEmpty()) {
                Thread {
                    val weatherData = WeatherAPI_Call().getWeatherData(lat, lon)

                    runOnUiThread {
                        if (weatherData != null) {
                            updateUI(weatherData)
                        } else {
                            Toast.makeText(this, "Error fetching data", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.start()
            }
        }
    }

    //para salvar a variável duarnte o recereate
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean("isDaytime", isDaytime)
    }

    class WeatherAPI_Call {
        fun getWeatherData(lat: String, lon: String): WeatherData? {
            return try {
                val urlString = "https://api.open-meteo.com/v1/forecast?latitude=$lat&longitude=$lon&current_weather=true&hourly=pressure_msl&timezone=auto"
                val response = URL(urlString).readText()

                Gson().fromJson(response, WeatherData::class.java)
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
    }

    private fun updateUI(data: WeatherData) {
        val current = data.current_weather

        // determinar se é dia ou noite
        val apiIsDay = current.is_day == 1

        runOnUiThread {
            val weatherImage: ImageView = findViewById(R.id.weatherIcon)
            val pressure: TextView = findViewById(R.id.pressureValue)

            findViewById<TextView>(R.id.temperature).text = "${current.temperature} ºC"
            findViewById<TextView>(R.id.windSpeed).text = "${current.windspeed} km/h"

            findViewById<TextView>(R.id.windDir).text = "${current.winddirection} º"

            val formattedTime = current.time.split("T").last()
            findViewById<TextView>(R.id.Time).text = formattedTime

            pressure.text = data.hourly.pressure_msl.get(12).toString() + " hPa"

            // icon
            val mapt = getWeatherCodeMap()
            val wCode = mapt.get(current.weathercode)

            val wImage = when (wCode) {
                WMO_WeatherCode.CLEAR_SKY,
                WMO_WeatherCode.MAINLY_CLEAR,
                WMO_WeatherCode.PARTLY_CLOUDY -> if (apiIsDay) wCode?.image + "day" else wCode?.image + "night"
                else -> wCode?.image
            }

            // aplicar icon
            val res = resources
            val resID = res.getIdentifier(wImage, "drawable", packageName)
            if (resID != 0) {
                val drawable = this.getDrawable(resID)
                weatherImage.setImageDrawable(drawable)
            }

            // aplicar mudanças
            if (apiIsDay != isDaytime) {
                isDaytime = apiIsDay
                recreate()
            }
        }
    }
}