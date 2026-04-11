# Assignment 1 — 1, 2 and 3

Course: DAM
Student(s): Miguel Duarte
Date: 14/03/2026

---

## 1. Introduction

This assignment demonstrates fundamental concepts of the Kotlin programming language, including array creation, functional operations, control flow, and console input/output.

The project contains three independent exercises:

1. Generation of arrays containing perfect squares.
2. Implementation of a console-based calculator supporting arithmetic, boolean, and bitwise operations.
3. Simulation of a decreasing sequence representing the height of a bouncing object.

The purpose of the assignment is to practice Kotlin syntax, data structures, lambda expressions, and basic program interaction through the command line.

---

## 2. System Overview

The project is divided into three packages:

* **exer_1** – Demonstrates different ways to create and initialize arrays in Kotlin.
* **exer_2** – Implements a console calculator with multiple types of operations.
* **exer_3** – Generates a sequence that simulates a bouncing ball losing height after each bounce.

Each exercise is implemented as an independent Kotlin program containing its own `main` function.

---

## 3. Architecture and Design

The system follows a simple procedural design, where each exercise is isolated within its own package.

### Exercise 1

Three approaches are used to generate the first 50 perfect squares:

* `IntArray` constructor
* `Range` combined with `map`
* Generic `Array` constructor

Each method demonstrates a different Kotlin approach to dynamically generating collections.

### Exercise 2

The calculator uses a loop-driven menu system that repeatedly prompts the user to select operations. A `when` expression is used to determine which operation to execute.

Supported operations include:

* Arithmetic operations (+, −, ×, ÷)
* Boolean logic (AND, OR, NOT)
* Bitwise shift operations (left shift, right shift)

User inputs are validated using nullable types and exception handling to avoid runtime errors.

### Exercise 3

A functional approach is used to generate a mathematical sequence using:

* `generateSequence()` to produce values dynamically
* A lambda function to simulate height reduction
* `takeWhile()` to stop when the value becomes smaller than 1

This models a bouncing object that loses 40% of its height after every bounce.

---

## 4. Implementation

### Exercise 1 – Perfect Squares Array

An array of size 50 is dynamically generated containing the squares of numbers from 1 to 50.

Three methods are demonstrated:

* **IntArray constructor**

```kotlin
val arrayA = IntArray(50) { (it + 1) * (it + 1) }
```

* **Range with map**

```kotlin
val arrayB = (1..50).map { it * it }
```

* **Generic Array constructor**

```kotlin
val arrayC = Array(50) { (it + 1) * (it + 1) }
```

Each array is iterated and printed to the console.

---

### Exercise 2 – Console Calculator

The calculator operates in an infinite loop and displays a menu allowing the user to choose an operation.

A `when` expression is used to route the selected operation.

Examples of supported operations:

**Arithmetic**

```kotlin
num1 + num2
num1 - num2
num1 * num2
num1 / num2
```

Division includes protection against division by zero using `ArithmeticException`.

**Boolean operations**

```kotlin
b1 && b2
b1 || b2
!b
```

**Bitwise shift operations**

```kotlin
num shl shift
num shr shift
```

User input is processed using:

```kotlin
readlnOrNull()?.toIntOrNull()
```

This helps avoid crashes due to invalid input.

---

### Exercise 3 – Sequence Generator

A sequence is generated starting at **100.0**, representing an initial height.

Each step multiplies the height by **0.6**, simulating a bounce where the object retains only 60% of its previous height.

```kotlin
val sequence = generateSequence(100.0) { height ->
    height * 0.6
}
```

The sequence continues until the value becomes smaller than **1**:

```kotlin
.takeWhile { it >= 1 }
```

Finally, the sequence is converted into a list and printed.

---

## 5. Testing and Validation

Testing was performed by executing each program independently.

### Exercise 1

* Verified that all arrays contain exactly 50 elements.
* Confirmed that values correspond to squares of numbers from 1 to 50.

### Exercise 2

Test cases included:

* Valid arithmetic operations
* Boolean inputs (`true` / `false`)
* Bit shift operations
* Division by zero
* Invalid numeric input

Exception handling correctly prevented crashes during invalid operations.

### Exercise 3

The sequence was verified to ensure:

* The first value is 100.
* Each subsequent value equals the previous value multiplied by 0.6.
* The sequence stops when values drop below 1.

---

## 6. Usage Instructions

### Requirements

* Kotlin installed
* IntelliJ IDEA or any Kotlin-compatible IDE

### Running the programs

Each exercise can be executed independently.

Example:

Run the main function in:

```
dam.exer_1.Main.kt
dam.exer_2.Main.kt
dam.exer_3.Main.kt
```

### Exercise 2 interaction

When running the calculator, a menu will appear:

```
=====Calculadora Simples=====
1 -> Adição
2 -> Subtração
3 -> Multiplicação
4 -> Divisão
5 -> AND
6 -> OR
7 -> NOT
8 -> Left Shift
9 -> Right Shift
0 -> Exit
```

Users must input the number corresponding to the desired operation and then provide the required operands.

