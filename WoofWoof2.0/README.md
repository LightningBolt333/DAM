# Assignment 3 — WoofWoof Multi-Module

Course: DAM
Student(s): Miguel Duarte
Date: 03/05/2026
Repository URL: https://github.com/LightningBolt333/DAM
---
## 1. Introduction
The purpose of this assignment is to evolve the "WoofWoof" application into a multi-module architecture, demonstrating a clear separation between business logic and UI presentation. The project explores the coexistence of two different UI frameworks—Android XML Views and Jetpack Compose—sharing a common core. The objective is to build a robust, maintainable system for browsing dog breeds while comparing modern and legacy Android development paradigms.

## 2. System Overview
WoofWoof is a dog-browsing application that allows users to explore various dog breeds. Main features include:
- **Dual UI Implementations**: Independent modules for XML-based and Compose-based interfaces.
- **Random Breed Discovery**: Fetches and displays random dog images from the Dog API.
- **Search by Breed**: Users can filter images by specific dog breeds.
- **Detail View**: Provides an expanded view of selected dog images.
- **State Management**: Robust handling of Loading, Success, and Error states across both UI implementations.
- **Cross-Framework Consistency**: Both apps consume the same data sources and business logic from a shared core.

## 3. Architecture and Design
The project implements a **Clean Architecture** approach using a multi-module setup:
- **`:core` (Android Library)**: The central source of truth. It contains data models, the `ApiClient` (using Ktor), and the `DogRepository`. It has no UI dependencies.
- **`:app-xml` (Android App)**: Implements the UI using traditional XML layouts, Activities, and `RecyclerView`.
- **`:app-compose` (Android App)**: Implements the UI using Jetpack Compose, featuring modern declarative UI patterns and transitions.
- **Design Patterns**: 
    - **MVVM (Model-View-ViewModel)**: Used in both app modules to handle UI logic.
    - **Unidirectional Data Flow (UDF)**: The UI reacts to a single `UiState` stream exposed by the ViewModel via `StateFlow`.
    - **Repository Pattern**: Abstracting data fetching from the ViewModels.

## 4. Implementation
- **Core Module**: Uses **Ktor** for asynchronous network requests and **Kotlin Serialization** for JSON parsing. This ensures a lightweight and multi-platform-ready networking layer.
- **XML Module**: Employs **ViewBinding** for type-safe layout access and **Glide** for efficient image loading and caching. It uses `ListAdapter` with `DiffUtil` for smooth list updates.
- **Compose Module**: Uses **Coil** for image loading. The UI is built with nested Composables, utilizing `Crossfade` for smooth transitions between the list and detail screens.
- **Shared Logic**: Both modules utilize a `MainViewModel` that communicates with the `DogRepository` to fetch data, ensuring identical behavior regardless of the UI layer.

## 5. Testing and Validation
- **Module Isolation**: Verified that `:core` remains UI-independent and that app modules do not depend on each other.
- **UI State Integrity**: Tested the state machine to ensure correct rendering of progress bars, error messages (with retry logic), and data grids.
- **Breed Search Logic**: Validated that both XML and Compose versions correctly handle API responses for various breed queries, including edge cases like invalid breed names.
- **Lifecycle Awareness**: Ensured that `StateFlow` collection in the XML module respects the lifecycle (using `repeatOnLifecycle`) to prevent memory leaks and unnecessary updates.

## 6. Usage Instructions
### Requirements
- Android Studio Iguana | 2023.2.1 or newer.
- Android SDK 24 (Nougat) or higher.
- Active internet connection for API requests.

### Setup and Execution
1. **Clone the repository**:
   ```bash
   git clone https://github.com/LightningBolt333/DAM.git
   ```
2. **Open the project**: Open the root folder in Android Studio and wait for Gradle sync to complete.
3. **Running the Apps**:
   - To run the **XML version**: Select the `app-xml` configuration from the run dropdown and click **Run**.
   - To run the **Compose version**: Select the `app-compose` configuration and click **Run**.
4. **Configuration**: The application uses the public Dog API (`dog.ceo`), so no additional API keys are required.
