# Assignment 3 — WoofWoof Multi-Module

Course: DAM
Student(s): Miguel Duarte
Date: 03/05/2026
Repository URL: https://github.com/LightningBolt333/DAM

---

## 1. Introduction
The objective of this assignment was to evolve the "WoofWoof" application from a monolithic structure into a robust multi-module architecture. The project demonstrates the separation of business logic and data handling from the UI layer, enabling the coexistence of two distinct UI implementations: one using traditional XML Views (`app-xml`) and another using Jetpack Compose (`app-compose`), both powered by a shared `core` module. This approach highlights modern Android development practices such as Clean Architecture and Unidirectional Data Flow (UDF).

## 2. System Overview
WoofWoof is a dog-browsing application that allows users to explore random dog images or search for specific breeds. High-level features include:
- **Dual UI Implementations**: Separate modules for XML and Compose providing different user experiences on the same data.
- **Random & Search Discovery**: Fetches dog images from the Dog API with support for breed-specific filtering.
- **Unified State Management**: Robust handling of Loading, Success, and Error states across both modules using a shared state object.
- **Offline-Ready Networking**: Uses Ktor for asynchronous data fetching and Kotlin Serialization for efficient parsing.

## 3. Architecture and Design
The project follows a Clean Architecture approach with a focus on modularity and separation of concerns.

### Module Diagram
The project is structured into three distinct modules to ensure a clean separation of concerns:
```text
[ :app-xml ] ------- depends on -------> [ :core ]
[ :app-compose ] --- depends on -------> [ :core ]
```
- **`:core`**: An Android library containing data models, the Ktor API client, and the `DogRepository`. It is completely UI-agnostic and contains no Android View or Compose dependencies.
- **`:app-xml`**: A traditional Android application module using XML layouts, ViewBinding, and Activities.
- **`:app-compose`**: A modern Android application module built entirely with Jetpack Compose.

### UI Contract
Both apps interact with the core via a shared `UiState` exposed by a ViewModel through a `StateFlow`:
- **State Structure**: The `UiState` holds a list of items, a `selectedDog` (null when in list view), an `isLoading` flag, and a nullable `errorMessage`.
- **UDF Pattern**: The UI never modifies the state directly; it only triggers functions on the ViewModel (e.g., `loadItems`, `selectItem`).
- **XML Implementation**: State is collected using `repeatOnLifecycle` to update RecyclerView adapters and show/hide progress bars or Snackbars.
- **Compose Implementation**: State is collected at the root (`MediaApp`) via `collectAsState` and passed to stateless child composables as immutable parameters.

## 4. Implementation

### Refactoring Plan
The transition from a monolithic architecture followed a structured, step-by-step roadmap:
1. **Extraction of Business Logic**: Moved data classes and Ktor networking logic into the new `:core` module.
2. **Repository Layer**: Built a `DogRepository` in `:core` to act as the single point of access, handling errors gracefully before they reach the UI.
3. **Legacy Migration**: Renamed the original app to `:app-xml` and refactored the ViewModel to consume the shared repository instead of direct API calls.
4. **Compose Evolution**: Developed `:app-compose` as a fresh module, implementing advanced features like animations and shimmers that are exclusive to this modern stack.

### Key Components
- **Networking**: Ktor Client with ContentNegotiation (JSON).
- **Compose Features**: Uses `Crossfade` for screen transitions, `AnimatedVisibility` for list entry animations, and `animateContentSize` for expanding details.
- **Loading UX**: Instead of a simple spinner, the Compose version uses shimmer placeholders to provide a more modern "skeleton" loading experience.

## 5. Testing and Validation
- **Module Isolation**: Verified that `:core` remains UI-independent and that app modules do not depend on each other.
- **UI State Integrity**: Tested the state machine to ensure correct rendering of progress bars, error messages (with retry logic), and data grids.
- **Breed Search Logic**: Validated that both XML and Compose versions correctly handle API responses for various breed queries, including edge cases like invalid breed names.
- **Resource Management**: Ensured all strings are externalized, supporting both English and Portuguese.

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
2. **Sync Project**: Open the project in Android Studio and wait for the Gradle sync to finish.
3. **Run XML App**: Select the `app-xml` configuration and click the **Run** button.
4. **Run Compose App**: Select the `app-compose` configuration and click the **Run** button.

### Documentation Specifications
The project architecture and decisions are further detailed in the `/docs` directory:
- `00_architecture.md`: Overview of the three-module system and data flow.
- `01_uiContract.md`: Detailed protocol for UI-ViewModel communication.
- `02_refactoringPlan.md`: Technical roadmap for the modularization process.
- `03_composeExclusive.md`: Specifications for modern UI features in the Compose module.
