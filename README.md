# Assignment 2 — CoolWeatherAPP

Course: DAM
Student(s): Miguel Duarte
Date: 11/04/2026
Repository URL: https://github.com/LightningBolt333/DAM

---

## 1. Introduction
CoolWeatherAPP is an Android application developed as part of the DAM course. The primary objective is to provide users with real-time weather information for any geographic location by entering its latitude and longitude. The app aims to demonstrate proficiency in API integration, JSON parsing, asynchronous programming, and dynamic UI management in Android.

## 2. System Overview
The application allows users to:
*   Input custom Latitude and Longitude coordinates.
*   Fetch current weather data including temperature, wind speed, wind direction, and sea-level pressure via the Open-Meteo API.
*   View weather-appropriate icons and background themes that dynamically change based on the time of day (Day/Night) and weather conditions.
*   Maintain application state across configuration changes such as screen rotation.

## 3. Architecture and Design
The project follows a standard Android architectural approach:
*   **Activity-Based**: A single `MainActivity` manages the user interface and coordinates data fetching.
*   **Networking**: Asynchronous API calls are performed using a background `Thread` to keep the UI responsive.
*   **Data Modeling**: Uses Kotlin data classes (`WeatherData`) to map JSON responses from the Open-Meteo API.
*   **Design Patterns**: Utilizes the Observer-like pattern for UI updates and manual State Management via `onSaveInstanceState`.
*   **UI/UX**: Responsive layout using `ConstraintLayout` with specialized themes for portrait, landscape, day, and night modes.

## 4. Implementation
*   **`MainActivity.kt`**: Contains the core logic for the application, including the `WeatherAPI_Call` inner class for networking and `updateUI` for reflecting data on the screen. It also handles theme switching by calling `recreate()` when a day/night transition is detected.
*   **`WeatherData.kt`**: Defines the structure for the weather data objects, used by `Gson` to deserialize the API response.
*   **API Integration**: Uses the Open-Meteo API with parameters for `current_weather` and `hourly` pressure data.
*   **Resource Management**: Implements custom XML drawables and distinct styles/themes to provide a polished visual experience.

## 5. Testing and Validation
*   **Functionality Testing**: Verified data retrieval for various global coordinates.
*   **UI Consistency**: Tested layout transitions between Portrait and Landscape orientations.
*   **Dynamic Theming**: Validated that the app correctly identifies "is_day" from the API and switches themes accordingly.
*   **Error Handling**: Basic toast notifications for failed network requests or data fetching errors.
*   **Edge Cases**: Handled empty input fields to prevent crashes during API calls.

## 6. Usage Instructions
1.  **Requirements**: Android Studio (latest version recommended) and an Android device or emulator running API 24 or higher with an active internet connection.
2.  **Setup**: Clone the repository from the provided URL.
3.  **Execution**:
    *   Open the project in Android Studio.
    *   Wait for Gradle Sync to complete.
    *   Build and run the app on your device/emulator.
4.  **Operation**:
    *   Enter the desired Latitude and Longitude in the input fields.
    *   Tap the "Update" button to fetch the latest weather data.
    *   The UI will update automatically with the current conditions and appropriate theme.
