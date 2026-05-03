# Assignment 3 — CoolJetpackWeatherApp

Course: DAM
Student(s): Miguel Duarte
Date: 03/05/2026
Repository URL: https://github.com/LightningBolt333/DAM
---
## 1. Introduction
CoolJetpackWeatherApp is an Android application developed with Jetpack Compose that provides real-time weather information. The project aims to demonstrate the integration of modern Android development tools, including asynchronous API consumption and responsive UI design.

## 2. System Overview
The application allows users to fetch current weather data for any location by inputting latitude and longitude coordinates. Key features include:
- Current temperature, wind speed, wind direction, and sea-level pressure display.
- Visual weather condition indicators (icons) based on WMO codes.
- Manual coordinate entry.
- Fully responsive layout supporting both portrait and landscape orientations.

## 3. Architecture and Design
The project follows the Model-View-ViewModel (MVVM) architectural pattern to ensure separation of concerns:
- **Data Layer (`data/`)**: Handles API communication using Ktor and data modeling with Kotlin Serialization.
- **ViewModel Layer (`viewmodel/`)**: Manages the UI state and business logic, fetching data within the `viewModelScope`.
- **UI Layer (`ui/`)**: Built entirely with Jetpack Compose, featuring modular components like `WeatherCard` and `CoordinatesCard`.
- **State Management**: Uses `StateFlow` to provide reactive updates from the ViewModel to the UI.

## 4. Implementation
- **WeatherApiClient**: An object using a Ktor `HttpClient` with `ContentNegotiation` to fetch JSON data from the Open-Meteo API.
- **WeatherViewModel**: Centralizes the application state in a `WeatherUIState` object, updating it as coordinates change or data is fetched.
- **Adaptive UI**: The `WeatherUI` composable detects device orientation and switches between `PortraitWeatherUI` and `LandscapeWeatherUI` to optimize screen real estate.
- **Data Parsing**: Uses `WMO_WeatherCode` mapping to translate numeric API codes into descriptive strings and corresponding drawable resources.

## 5. Testing and Validation
- **Manual UI Testing**: Verified that the layout adapts correctly when rotating the device.
- **Input Validation**: Coordinates are converted to floats, ensuring the API receives valid parameters.
- **Edge Cases**: Handled scenarios where pressure data is extracted from the hourly forecast matching the current hour.
- **Limitations**: The app requires an active internet connection and does not currently implement local database caching or GPS-based location detection.

## 6. Usage Instructions
1. **Requirements**: Android Studio Jellyfish or newer, JDK 17+, and an internet connection.
2. **Setup**: Clone the repository and sync the Gradle files.
3. **Configuration**: Ensure the device or emulator has internet access.
4. **Execution**: Run the `app` module. Enter latitude (e.g., 38.7) and longitude (e.g., -9.1) and press "Update" to see the weather for that location.
