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

### Prompts Used
#### Phase 1 - Create the core module
##### Prompt 1.1
Create a new Android Library module called core. It should have no UI code at all. Add the dependencies needed for making HTTP requests with Ktor and for JSON serialization with kotlinx.serialization. Just set up the module and sync the project, no code yet.
##### Prompt 1.2
Move all the data classes that represent the app's data into the core module. Make sure they are properly annotated for JSON serialization. Do not change any field names. Update imports wherever those classes were being used. Do not touch any UI files. Make sure everything still compiles.
##### Prompt 1.3
Move the code responsible for making API calls into the core module. Update imports wherever that code was being referenced. Do not touch any UI files. Make sure everything still compiles.
##### Prompt 1.4
Create a repository in the core module that acts as the single point of access to the app's data. It should expose functions to get a list of items and to get a single item by id. It should handle errors gracefully so the rest of the app never has to deal with raw exceptions. Do not touch any UI files. Make sure everything still compiles.
#### Phase 2 - Clean up the existing app
##### Prompt 2.1
Rename the existing app module to app-xml and make it depend on the core module. The app should still run exactly as before after this change.
##### Prompt 2.2
Refactor the ViewModel in app-xml so it no longer handles data fetching directly. It should use the repository from core instead. It should expose a single state object to the UI that contains the list of items, which item is currently selected if any, whether the app is loading, and any error message that needs to be shown.
##### Prompt 2.3
Update the UI in app-xml to work with the new ViewModel state. Loading, errors, and the item list should all be driven by that state. The visual result should be identical to before.
#### Phase 3 - Build the Compose app
##### Prompt 3.1
Create a new Android Application module called app-compose. Add Jetpack Compose and the necessary dependencies. Make it depend on the core module. Just get a blank screen running for now.
##### Prompt 3.2
Add a ViewModel to app-compose that works the same way as the one in app-xml, using the same repository from core.
##### Prompt 3.3
Build the main screens of the app in Jetpack Compose. There should be a list screen that shows all the items, handles loading and error states, and lets the user tap an item. There should also be a detail screen that shows the information for a single item and lets the user go back. Keep the composables simple and stateless, just receiving data and reacting to user actions.
##### Prompt 3.4
Wire everything together so the app navigates between the list and detail screens based on what the user taps. The ViewModel should drive all of it.
##### Prompt 3.5
Add animations to the list screen. Items should animate in when the screen first loads. The transition between the list and the detail screen should also be animated instead of cutting straight to it.
##### Prompt 3.6
While the app is loading, instead of a plain spinner, show placeholder cards that have a subtle shimmer effect. When the real content arrives, animate the swap between the placeholders and the actual list.
#### Phase 4 - Finishing touches
##### Prompt 4.1
Make sure every piece of text shown to the user comes from string resources and not hardcoded strings. Add both an English and a Portuguese version of all strings.
##### Prompt 4.2
Do a final review of the whole project. Check that the core module has no UI code, that the two app modules do not share code directly between themselves, that no composable below the root screen holds a reference to the ViewModel, and that the animations in app-compose do not exist in app-xml. Fix anything that looks wrong and make sure the project builds cleanly.