# Architecture

The app follows the **MVVM (Model-View-ViewModel)** design pattern to ensure separation of concerns.

### Components
- **View (Activity/XML)**: Observes the ViewModel and updates the UI. Handles user interactions.
- **ViewModel**: Manages UI state (Loading, Success, Error) and handles communication with the Repository.
- **Repository**: Single source of truth for data. Fetches data from the Remote Data Source (API).
- **Remote Data Source**: Retrofit interface for the Dog API.