## Architecture Overview
- This project takes the existing media browser app from WoofWoof and splits it into three modules. The goal is to have one shared core module that handles all the data and business logic, and two separate app modules that each provide a different UI on top of it.
- The three modules are called core, app-xml, and app-compose.
- The core module is an Android library. It contains the data classes, the API client built with Ktor, and the repository. It has no UI code whatsoever. Both app modules depend on it.
- The app-xml module is basically the app after being cleaned up. It keeps the XML layouts and Activities but strips out any data or API logic that now lives in core.
- The app-compose module is a brand new app that uses Jetpack Compose for the entire UI. - It consumes the same core module as app-xml but presents the data in a completely different way, including animations that do not exist in the XML version.
- Neither app module knows about the other. They only know about core.
- The data always flows in one direction: the API client fetches data, the repository wraps it and handles errors, the ViewModel transforms it into UI state, and the UI just reads that state and reacts to it. The UI never calls the API directly.