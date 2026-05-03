# Assignment 3 — Greeting Processor Project

Course: DAM
Student(s): Miguel Duarte
Date: 03/05/2026
Repository URL: https://github.com/LightningBolt333/DAM
---
## 1. Introduction
This project implements a Kotlin annotation processing example for the DAM course. The main objective was to create custom annotations and processors that generate Kotlin source code during compilation.

The assignment focuses on two use cases: generating wrapper classes for methods annotated with greeting messages, and generating regex-based data extractor classes from abstract methods annotated with extraction patterns.
## 2. System Overview
The system is a Gradle multi-module Kotlin project composed of an annotation module, an annotation processor module, and a small application module that demonstrates the generated code.

The `@Greeting` annotation marks functions that should be wrapped by generated code. For each class containing these annotated methods, the processor generates a wrapper class that prints the configured greeting message before delegating to the original method.

The `@Extract` annotation marks abstract functions that should extract values from an input string using regular expressions. The processor generates a concrete extractor class that extends the original abstract class and implements each annotated method by applying its regex and returning the first captured group.
## 3. Architecture and Design
The project is organized into three Gradle modules:

`annotations` contains the custom source-retention annotations `Greeting` and `Extract`.

`processor` contains the annotation processors `GreetingProcessor` and `RegexProcessor`. These processors are registered with AutoService and generate Kotlin source files using KotlinPoet.

`app` contains example application classes that use the annotations and depend on the processor through KAPT.

The design separates annotation definitions from processing logic so the application can depend only on the annotation API while KAPT runs the processors at compile time. Generated code is written to KAPT's Kotlin generated source directory, allowing it to be compiled together with the application.
## 4. Implementation
The `annotations` module defines `@Greeting(message: String)` for functions and `@Extract(regex: String)` for functions. Both annotations use source retention because they are only needed during compilation.

`GreetingProcessor` scans the compilation round for methods annotated with `@Greeting`, groups them by their enclosing class, and generates one wrapper class per original class. For example, `MyClass` produces `MyClassWrapper`, which receives the original `MyClass` instance through its constructor. Each generated wrapper method prints the annotation message and then calls the matching method on the original object.

`RegexProcessor` scans for methods annotated with `@Extract`, groups them by enclosing class, and generates one extractor class per abstract processor class. For `DataProcessor`, it generates `DataProcessorExtractor`, which extends `DataProcessor`, receives the input string in its constructor, and overrides `getName()` and `getAddress()` by applying the configured regular expressions.

The application demonstrates the extraction workflow in `Main.kt` by creating a `DataProcessorExtractor` with the input string `" Name : John Address : 123 Street "`, then printing the extracted name and address.
## 5. Testing and Validation
Validation was done through compilation and generated source inspection. The build output contains generated files for both processors: `DataProcessorExtractor.kt` and `MyClassWrapper.kt`.

The main executable scenario validates that `DataProcessorExtractor` can extract `John` from the `Name : (\\w+)` pattern and `123 Street` from the `Address : (.+)` pattern. The generated wrapper scenario validates that greeting methods are generated for `sayHello()` and `compute()`.

There are no dedicated automated test classes in the project. Known limitations include simple regex extraction that always returns capture group 1, no explicit validation that annotated `@Extract` methods contain compatible signatures, and generated greeting wrapper methods that currently delegate to original methods without returning values.
## 6. Usage Instructions
Requirements: JDK 23, Gradle through the included wrapper, and Kotlin/JVM support provided by the Gradle configuration.

To build the project, run:

```bash
./gradlew build
```

On Windows PowerShell, run:

```powershell
.\gradlew.bat build
```

To run the application entry point from the `app` module, use the Gradle run task if an application plugin or run configuration is added, or run `com.example.app.MainKt` from IntelliJ IDEA after importing the Gradle project. During compilation, KAPT runs the processors and places generated Kotlin files under `app/build/generated/source/kaptKotlin/main`.
