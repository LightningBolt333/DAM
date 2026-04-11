# Assignment 2 — WoofWoof

Course: DAM
Student(s): Miguel Duarte
Date: 11/04/2026
Repository URL: https://github.com/LightningBolt333/DAM

---

## 1. Introduction
WoofWoof is an Android application designed for dog enthusiasts to discover and browse random images of various dog breeds. The project serves as a practical implementation of modern Android development practices, focusing on networking, asynchronous data handling, and clean architecture. The primary objective is to provide a seamless user experience for viewing high-quality dog photos fetched from the public Dog API.

## 2. System Overview
The application allows users to:
- **Browse Random Dogs**: View a dynamic grid of dog images.
- **Search by Breed**: Filter images to see specific dog breeds.
- **Swipe-to-Refresh**: Easily fetch a new set of random images using a pull-to-refresh gesture.
- **Offline Support (Caching)**: Uses Glide for efficient image caching and smooth scrolling.
- **Error Handling**: Provides user-friendly feedback and retry options in case of network failures.

## 3. Architecture and Design
The project follows the **MVVM (Model-View-ViewModel)** architectural pattern to ensure a clean separation of concerns and maintainability.

- **Presentation Layer**: `MainActivity` and XML layouts handle the UI and user interactions.
- **ViewModel Layer**: `MainViewModel` manages the UI state (Loading, Success, Error) and survives configuration changes.
- **Repository Layer**: `DogRepository` acts as a single source of truth, abstracting the data source from the rest of the app.
- **Data Layer**: Retrofit handles API communication, while Gson parses the JSON responses.

**Folder Structure:**
- `ui/`: Contains Activities, ViewModels, and Adapters.
- `data/`: Contains API service interfaces, Data Transfer Objects (DTOs), and Repositories.

## 4. Implementation
Key components include:
- **Networking**: Implemented with **Retrofit** and **OkHttp** to communicate with `https://dog.ceo/api/`.
- **Image Loading**: **Glide** is used for fetching and caching remote images into `ImageView` components.
- **Concurrency**: Kotlin **Coroutines** and `StateFlow` are used for non-blocking network calls and reactive UI updates.
- **UI Components**: `RecyclerView` with `ListAdapter` and `DiffUtil` for efficient list rendering, and `SwipeRefreshLayout` for data updates.

Relevant code excerpts include the `UiState` sealed class for robust state management and the `DogRepository` for clean data fetching logic.

## 5. Testing and Validation
- **UI State Testing**: Validated that the app correctly transitions between Loading, Success (displaying images), and Error (showing retry button) states.
- **Search Validation**: Tested various breed names (e.g., "hound", "retriever") to ensure the API returns correct results or handles "not found" errors gracefully.
- **Network Resilience**: Verified that the app displays a clear error message when the device is offline and allows the user to retry once the connection is restored.
- **Edge Cases**: Handled empty responses and invalid API endpoints.

## 6. Usage Instructions
### Requirements
- Android Studio Iguana or newer.
- Android SDK 24 (Nougat) or higher.
- Active Internet connection.

### Setup and Execution
1. **Clone the repository**:
   ```bash
   git clone https://github.com/LightningBolt333/DAM.git
   ```
2. **Open in Android Studio**: Select the project folder and wait for Gradle synchronization.
3. **Run the App**:
   - Connect an Android device or start an emulator.
   - Click the **Run** button (green play icon) in Android Studio.
4. **Configuration**: No additional API keys are required as the Dog API is public.
