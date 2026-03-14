# Assignment 1 — XARD Calamity Files
Course: DAM
Student(s): Miguel Duarte
Date: 14/03/2026
---

## 1. Introduction
XARD Calamity Files is an Android application designed to manage character profiles and their respective abilities. The primary objective is to provide a structured way to create, edit, and view detailed character information, including stats, classes, and dynamic sets of abilities like passives and effects.

## 2. System Overview
The application allows users to:
*   **Create and Edit Characters:** Input basic details like name, class (Attacker, Controller, etc.), and subclass.
*   **Manage Abilities:** Add a fixed set of core abilities (Basic Attack, Supreme) and a dynamic number of Passives and Effects.
*   **Media Integration:** Attach profile pictures and ability icons using the device's image picker with persistent URI permissions.
*   **Data Persistence:** Store all character and ability data locally using an SQLite database.
*   **Markdown Support:** (Future/Planned) Render character lore and descriptions using Markdown.

## 3. Architecture and Design
The project follows the **MVVM (Model-View-ViewModel)** architectural pattern:
*   **View:** Activities and Fragments (`MainActivity`, `CharacterCreateFragment`, etc.) handle UI and user interactions using ViewBinding.
*   **ViewModel:** `CreateViewModel` and others manage UI state and interact with the data layer.
*   **Repository:** `CharacterRepository` acts as a single point of truth for data operations.
*   **Model:** Data classes (`Character`, `Ability`) representing the domain entities.
*   **Local Data:** Room Persistence Library for database management (`AppDatabase`, `CharacterDao`).

**Folder Structure:**
*   `ui/`: Fragments and UI logic grouped by feature (list, create, details).
*   `data/`: Database entities, DAOs, and the Repository.
*   `content/viewmodel/`: ViewModels and Factories.
*   `utils/`: Helper classes (e.g., `UriUtils` for permission handling).

## 4. Implementation
*   **Room Database:** Uses a 1-to-N relationship between `Character` and `Ability`.
*   **Navigation Component:** Handles fragment transitions and argument passing via SafeArgs.
*   **Coil:** Used for efficient asynchronous image loading and transformations (CircleCrop).
*   **KSP:** Kotlin Symbol Processing for Room code generation.
*   **Dynamic UI:** The character creation screen uses `LayoutInflater` to dynamically add/remove ability input fields based on user interaction.

## 5. Testing and Validation
*   **Unit Testing:** JUnit 4 for testing business logic in Repositories and ViewModels.
*   **UI Testing:** Espresso for testing user flows like character creation.
*   **Manual Review:** Verification of image URI persistence and database integrity across app restarts.

## 6. Usage Instructions
1.  **Requirements:** Android Studio Jellyfish or later, JDK 17.
2.  **Setup:** Clone the repository and open it in Android Studio.
3.  **Configuration:** Ensure Gradle sync completes successfully.
4.  **Execution:** Run the `:app` module on an Android Emulator or physical device (Min SDK 24).

---
## 7. Prompting Strategy
The project was initiated with a comprehensive "Greenfield" prompt designed to guide an autonomous agent through a full development lifecycle. The strategy involved:
1.  **High-Level Goal Setting:** Defining the "XARD Calamity Files" domain and its core feature set (complex character profiles, dynamic ability lists, and media support).
2.  **Constraint Enforcement:** Explicitly requiring modern standards like Kotlin, XML-based Views, Material Design 3, and MVVM architecture.
3.  **Procedural Prompting:** Requiring a detailed project plan for approval before any code was generated.
4.  **Feature-Specific Deep-Dives:** Iterative prompting to handle complex requirements like Markdown integration and persistent media URI management.

## 8. Autonomous Agent Workflow
The AI agent acted as the primary software engineer, following a structured workflow:
*   **Planning & Architecture:** Generated a project blueprint including the folder structure, dependency list (Coil, Room, Navigation), and data relationship models.
*   **Code Generation:** Implemented the entire greenfield application from scratch, including database schemas, UI layouts, and state management.
*   **Problem Solving:** Autonomously identified and resolved build-system conflicts (Gradle/AGP version mismatches) and compilation errors.
*   **Refinement:** Optimized UI responsiveness and ensured adherence to Material Design 3 guidelines.

## 9. Verification of AI-Generated Artifacts
Verification was conducted through a multi-layered approach:
*   **Architectural Approval:** The human developer reviewed the AI-generated project plan to ensure it met scalability and maintainability needs.
*   **Static Analysis:** Used Android Studio's built-in inspections to verify code quality and find potential resource leaks or duplicate imports.
*   **Integration Testing:** Validated the complex 1:N data relationships (Character to Abilities) by performing full Create-Read-Update cycles in the app.
*   **Build Validation:** Continuous verification of the build process using Gradle to ensure stability across version changes.

## 10. Human vs AI Contribution
*   **AI (85%):** Responsible for architectural design, the vast majority of implementation (Kotlin/XML), dependency management, and technical documentation.
*   **Human (15%):** Defined the initial vision, provided the greenfield prompt, reviewed/approved the project plan, and performed final deployment and manual testing.

## 11. Ethical and Responsible Use
Development focused on security and transparency:
*   **Data Privacy:** Used Scoped Storage principles and `takePersistableUriPermission` to handle user media securely without requiring broad storage permissions.
*   **Tool Disclosure:** All AI tools and the nature of their contribution are clearly documented to maintain academic and professional integrity.
*   **Code Ownership:** The developer reviewed all AI suggestions to ensure full understanding and responsibility for the final codebase.

---
# Development Process
## 12. Version Control and Commit History
Git is used for version control. The history includes feature-specific commits, bug fixes for the build system, and UI layout iterations.

## 13. Difficulties and Lessons Learned
*   **Challenge:** Gradle version conflicts can be obscure.
*   **Lesson:** Staying on the "bleeding edge" (Gradle 9.x) can lead to compatibility issues with standard Android plugins; stability is often preferable.
*   **Challenge:** Managing URIs across app sessions.
*   **Lesson:** Learned the importance of `takePersistableUriPermission`.

## 14. Future Improvements
*   Implement a search filter for the character list.
*   Add export/import functionality for character data (JSON).
*   Full Markwon integration for rich-text character biographies.

---
## 15. AI Usage Disclosure (Mandatory)
This project was developed by an Autonomous Software Engineering Agent (Antigravity) based on a greenfield development prompt. The AI was responsible for end-to-end architecture planning, implementation of core features, and troubleshooting build-level configuration issues. The human developer acted as a project lead, reviewing all AI outputs and ensuring the final application met all quality and functional requirements.
