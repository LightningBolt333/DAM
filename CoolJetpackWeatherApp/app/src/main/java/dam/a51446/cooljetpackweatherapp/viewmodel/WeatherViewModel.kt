package dam.a51446.cooljetpackweatherapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dam.a51446.cooljetpackweatherapp.data.WeatherApiClient
import dam.a51446.cooljetpackweatherapp.ui.WeatherUIState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(WeatherUIState())
    val uiState: StateFlow<WeatherUIState> = _uiState.asStateFlow()

    fun updateLatitude(lat: Float) {
        _uiState.update { it.copy(latitude = lat) }
    }

    fun updateLongitude(lon: Float) {
        _uiState.update { it.copy(longitude = lon) }
    }

    fun fetchWeather() {
        viewModelScope.launch {
            val data = WeatherApiClient.getWeather(
                _uiState.value.latitude,
                _uiState.value.longitude
            )
            data?.current_weather?.let { current ->

                //encontrar indice da hora atual nos dados hourly
                val currentHour = current.time.substring(0, 13) //"2026-05-02T14"
                val currentTimeIndex = data.hourly.time.indexOfFirst {
                    it.substring(0, 13) == currentHour
                }
                val pressure = if (currentTimeIndex >= 0)
                    data.hourly.pressure_msl[currentTimeIndex].toFloat()
                else
                    0f

                _uiState.update { it.copy(
                    temperature = current.temperature,
                    windspeed = current.windspeed,
                    winddirection = current.winddirection,
                    weathercode = current.weathercode,
                    time = current.time,
                    seaLevelPressure = pressure
                ) }
            }
        }
    }
}