# Assignment X — Device Info

Course: DAM
Student(s): Miguel Duarte
Date: 14/03/2026

---

## 1. Introduction
**Device Info** is a straightforward Android application designed to provide users with a detailed overview of their device's hardware and software specifications. It serves as a utility to quickly access system properties that are often nested deep within the device settings.

## 2. System Overview
The system is a single-screen mobile application. Upon launch, it automatically queries the Android operating system for various build and version parameters and displays them in a clean, scrollable text format.

## 3. Architecture and Design
The application follows a standard Android Activity-based architecture:
- **View:** Defined in `activity_main.xml`, utilizing a `ScrollView` to ensure all information is accessible regardless of screen size, and a `TextView` to display the data.
- **Controller:** `MainActivity.kt` manages the lifecycle of the application, fetches the device data, and populates the view.

## 4. Implementation
The implementation relies on the `android.os.Build` class provided by the Android SDK. Key information fetched includes:
- **Hardware details:** Brand, Model, Manufacturer, Hardware, and Board.
- **Software details:** Android Version (Release), API Level, and Build ID.
- **Identification:** Fingerprint and Host information.

The data is gathered during the `onCreate` phase and rendered using a `trimIndent` string block for clean formatting.

## 5. Testing and Validation
Validation was performed by:
- Verifying that all `Build` fields return non-null values on the target device/emulator.
- Ensuring the `ScrollView` functions correctly when the text content exceeds the screen height.
- Confirming that the `textIsSelectable` property allows users to interact with and copy the displayed information.

## 6. Usage Instructions
1. Clone the repository and open the project in **Android Studio**.
2. Sync the project with Gradle files.
3. Connect an Android device or start an emulator.
4. Click **Run 'app'** to install and launch the application.
5. The device information will be displayed immediately upon startup.

---
