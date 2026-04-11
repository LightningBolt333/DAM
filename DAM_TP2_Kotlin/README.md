# Assignment 2 — Kotlin Advanced Features

**Course:** DAM  
**Student(s):** Miguel Duarte  
**Date:** 11/04/2026  
**Repository URL:** [https://github.com/LightningBolt333/DAM](https://github.com/LightningBolt333/DAM)

---

## 1. Introduction
The purpose of this assignment is to explore and implement fundamental and advanced Kotlin programming concepts. The project focuses on leveraging Kotlin’s expressive syntax to solve common software engineering challenges, such as state management, generic data storage, sequential data processing, and mathematical abstractions. 

The objectives include:
* Mastering **Sealed Classes** and **Data Classes** for type-safe modeling.
* Implementing **Generics** and **Higher-Order Functions** for reusable components.
* Utilizing **Operator Overloading** to create intuitive mathematical libraries.
* Applying **Extension Functions** and **Function Literals with Receivers** to build DSL-like structures.

---

## 2. System Overview
The solution is divided into four distinct exercises, each targeting a specific Kotlin feature set:
* **Event System (`Event.kt`):** A log-processing utility that tracks user actions (Login, Purchase, Logout) and calculates metrics like total spending.
* **Generic Cache (`Cache.kt`):** A flexible, type-safe in-memory storage system that supports automatic value computation (`getOrPut`) and functional transformations.
* **Data Pipeline (`Pipeline.kt`):** A serial processing engine where string data passes through a sequence of stages (filtering, formatting) defined via a type-safe builder.
* **2D Vector Library (`Vec2.kt`):** A mathematical utility for 2D coordinate calculations using standard algebraic operators.

---

## 3. Architecture and Design
The project follows a modular package structure organized by exercise:
* `dam.ex1_1`: Event handling and functional filtering.
* `dam.ex1_2`: Generic data structures.
* `dam.ex1_3`: The Pipeline design pattern.
* `dam.ex1_4`: Operator overloading and geometry.

### Key Design Decisions:
* **Sealed Hierarchies:** Used for `Event` to ensure exhaustive `when` expressions, preventing unhandled event types at compile time.
* **Composition over Inheritance:** The `Pipeline` class allows for dynamic behavior by composing small transformation functions.
* **Immutability:** Data classes and immutable list returns are prioritized to ensure thread safety and predictable state.

---

## 4. Implementation
### Main Modules & Code Excerpts

#### **Event Processing**
Utilizes **Extension Functions** to add logic to standard Collections without modifying their source code:
```kotlin
fun List<Event>.totalSpent(username: String): Double {
    return this.filterIsInstance<Event.Purchase>()
               .filter { it.username == username }
               .sumOf { it.amount }
}
```

#### **Generic Cache**
Features **Higher-Order Functions** to allow users to define how data is transformed or initialized:
```kotlin
fun getOrPut(key: K, default: () -> V): V {
    val current = get(key)
    return current ?: default().also { put(key, it) }
}
```

#### **Execution Pipeline**
Implements a **Type-Safe Builder** (`Pipeline.() -> Unit`) allowing for a clean, declarative configuration in the `main` function:
```kotlin
val logPipeline = buildPipeline {
    addStage("Trim") { list -> list.map { it.trim() } }
    addStage("Filter errors") { list -> list.filter { it.contains("ERROR") } }
}
```

#### **Vector Math**
Employs **Operator Overloading** to allow natural syntax for vector addition and scaling:
```kotlin
operator fun plus(other: Vec2) = Vec2(x + other.x, y + other.y)
operator fun times(scalar: Double) = Vec2(x * scalar, y * scalar)
```

---

## 5. Testing and Validation
Validation is performed via the `main` functions included in each file, covering:
* **Functional Scenarios:** Filtering events for specific users and calculating total purchases for Alice and Bob.
* **State Integrity:** Testing cache eviction, size tracking, and "missing key" transformations in the `wordCache`.
* **Data Transformation:** Running a log of raw strings through a multi-stage pipeline to verify trimming, filtering, and indexing.
* **Mathematical Accuracy:** Validating vector magnitudes and normalization (including edge cases like zero-vector prevention).

**Known Limitations:**
* The `Cache` is not thread-safe (uses `mutableMapOf` without synchronization).
* The `Pipeline` assumes all stages return a list of the same type (`String`).

---

## 6. Usage Instructions
### Requirements
* **JDK 11** or higher.
* **Kotlin Compiler** (kotlinc) or an IDE like **IntelliJ IDEA**.

### Execution Steps
1.  **Clone the Repository:**
    ```bash
    git clone https://github.com/LightningBolt333/DAM
    ```
2.  **Compile and Run:**
    * To run the Event system: `kotlinc Event.kt -include-runtime -d Event.jar && java -jar Event.jar`
    * Repeat similarly for `Cache.kt`, `Pipeline.kt`, and `Vec2.kt`.
3.  **IDE:** Open the project folder in IntelliJ IDEA; the IDE will automatically detect the Kotlin files and provide "Run" icons next to each `main` function.