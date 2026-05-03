package dam.a51446.cooljetpackweatherapp.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WeatherCard(
    temperature: Float,
    windSpeed: Float,
    windDirection: Int,
    seaLevelPressure: Float,
    time: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
    ) {
        Column(modifier = modifier.padding(16.dp)) {
            WeatherRow(label = "Sea Level Pressure", value = "$seaLevelPressure hPa")
            WeatherRow(label = "Wind Direction", value = "$windDirection°")
            WeatherRow(label = "Wind Speed", value = "$windSpeed km/h")
            WeatherRow(label = "Temperature", value = "$temperature°C")
            WeatherRow(label = "Time", value = time)
        }
    }
}
