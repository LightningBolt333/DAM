# Assignment 1 — Hello World V2 (Drawing App)
Course: DAM 
Student(s): Miguel Duarte 
Date: 14/03/2026

---

## 1. Introduction
This application is a simple drawing tool developed for Android. Its primary purpose is to provide a digital canvas where users can draw freehand using touch gestures and save their work directly to the device's image gallery.

## 2. System Overview
The system is built as a single-activity Android application. The core functionality is encapsulated in a custom `DrawingView` which handles user input and rendering. A "Save" button allows the user to export the current canvas state as an image file.

## 3. Architecture and Design
The application follows a modular design by separating the drawing logic into a dedicated custom View:
- **MainActivity**: Serves as the entry point and coordinates between the UI components (DrawingView and Save Button).
- **DrawingView**: A custom UI component extending `View`. It manages its own state (the `Path` being drawn) and handles touch events to update the canvas.
- **Resources**: UI layout is defined in XML, supporting both portrait and landscape orientations.

## 4. Implementation
- **Drawing Logic**: The `DrawingView` class uses `android.graphics.Path` to store lines and `android.graphics.Paint` to define the stroke style (black color, 10f width, rounded caps). It overrides `onTouchEvent` to track finger movement and `onDraw` to render the path.
- **Image Export**: The `getBitmap()` method in `DrawingView` creates a `Bitmap` from the current view state. `MainActivity` then uses `MediaStore.Images.Media.insertImage` to save this bitmap to the device.
- **Lifecycle & UI**: The app uses `enableEdgeToEdge()` for a modern look and responds to window insets to ensure the UI is not obscured by system bars.

## 5. Testing and Validation
- **Functional Testing**: Verified that touch events accurately translate to lines on the screen and that the "Save" functionality correctly writes files to storage.
- **UI Testing**: Checked layout consistency across different screen orientations (portrait and landscape).
- **Unit Testing**: Includes a standard `ExampleUnitTest` for basic logic verification.

## 6. Usage Instructions
1. **Draw**: Launch the app and use your finger to draw anywhere on the screen.
2. **Save**: Once finished, tap the **SAVE** button at the bottom of the screen.
3. **Verify**: Look for the "Saved!" toast message. The drawing will now be available in your device's Gallery/Photos app.
4. **Clear**: The canvas automatically clears after a successful save, allowing for a new drawing.

---
