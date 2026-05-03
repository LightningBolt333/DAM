## UI Contract

- This document describes how the two app modules talk to the core module and how the UI is expected to behave.
- Both app modules use a ViewModel that holds a single UI state object. That state object has a list of media items, a selected item which can be null, a boolean for loading, and a string for error messages which is also nullable.
- The ViewModel exposes that state as a StateFlow. The UI collects it and redraws whenever it changes. The UI never modifies state directly. It only calls functions on the ViewModel like loadItems, selectItem, or clearError.
- In the XML app, the Activity or Fragment collects the StateFlow using repeatOnLifecycle and updates the RecyclerView adapter, shows or hides a loading spinner, and shows a Snackbar when there is an error message. When the Snackbar is shown it calls clearError on the ViewModel so the same error does not show again.
- In the Compose app, the root composable called MediaApp collects the StateFlow with collectAsState and passes the values down to child composables as plain parameters. The child composables are all stateless. They receive data and lambdas, nothing else. None of them hold a reference to the ViewModel directly.
- When the selected item is null the app shows the list screen. When it is not null the app shows the detail screen. Going back sets the selected item back to null.
- All text shown to the user must come from string resources. No hardcoded strings anywhere in layouts or composables. The app needs at least English and Portuguese string files.