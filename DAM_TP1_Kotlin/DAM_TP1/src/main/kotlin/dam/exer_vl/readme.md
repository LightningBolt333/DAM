# Assignment — Kotlin Library Management System

Course: DAM
Student(s): Miguel Duarte
Date: 14/03/2026

---

## 1. Introduction

This assignment implements a simple **Library Management System in Kotlin** to demonstrate object-oriented programming concepts such as abstraction, inheritance, encapsulation, and data classes.

The system models different types of books and a library that manages them. It allows users to add books, borrow and return them, display the catalog, and search books by author.

The project also demonstrates Kotlin-specific features such as custom getters and setters, companion objects, and data classes.

---

## 2. System Overview

The system simulates the basic functionality of a library.

Main features include:

* Representation of books using an abstract base class
* Support for **digital books** and **physical books**
* Library catalog management
* Borrowing and returning books
* Searching books by author
* Displaying book information and storage details

The program runs as a console application where operations are executed through predefined actions in the `main()` function.

---

## 3. Architecture and Design

The system is structured around several classes with clear responsibilities.

### Book (Abstract Class)

The `Book` class represents the base structure for all book types. It contains common attributes:

* `title`
* `author`
* `publicationYear`
* `availableCopies`

It also includes:

* A **computed property** (`era`) that classifies books based on publication year:

    * Classic (before 1980)
    * Modern (1980–2010)
    * Contemporary (after 2010)

* A **custom setter** for `availableCopies` that prevents negative values and warns when stock reaches zero.

The class also declares the abstract method:

```
getStorageInfo()
```

which must be implemented by subclasses.

---

### DigitalBook

`DigitalBook` extends `Book` and represents books stored digitally.

Additional properties include:

* `fileSize`
* `format`

It overrides `getStorageInfo()` to describe digital storage information.

---

### PhysicalBook

`PhysicalBook` represents printed books.

Additional properties include:

* `weight`
* `hasHardcover`

The `getStorageInfo()` method returns information about the book's physical characteristics.

---

### Library

The `Library` class manages a collection of books using a mutable list.

Main responsibilities:

* Adding books to the catalog
* Borrowing books
* Returning books
* Displaying all books
* Searching books by author

The library ensures that books cannot be borrowed if no copies are available.

---

### LibraryMember

A `data class` represents a library member.

Attributes include:

* `name`
* `membershipId`
* `borrowedBooks`

Although defined, it is not actively used in the current version of the program but demonstrates the use of Kotlin data classes for modeling entities.

---

## 4. Implementation

### Abstract Book Class

The base class provides shared properties and logic for all books.

Example:

```kotlin
abstract class Book(val title: String, val author: String, val publicationYear: Int, initialCopies: Int)
```

The `era` property is computed dynamically:

```kotlin
val era: String get() = when {
    publicationYear < 1980 -> "Classic"
    publicationYear in 1980..2010 -> "Modern"
    else -> "Contemporary"
}
```

The setter for `availableCopies` prevents invalid values and warns when stock reaches zero.

---

### Borrowing Books

The library searches for books by title and decreases the available copies when borrowed.

Example:

```kotlin
if(book.availableCopies > 0){
    book.availableCopies--
}
```

If no copies remain, the system notifies the user.

---

### Returning Books

Returning a book increases the number of available copies:

```kotlin
book.availableCopies++
```

The system verifies that the book belongs to the library before accepting the return.

---

### Searching Books by Author

Books can be filtered using Kotlin's `filter()` function:

```kotlin
val results = books.filter { it.author.equals(author, ignoreCase = true) }
```

Matching books are then displayed.

---

## 5. Testing and Validation

The program was tested using the example scenario defined in the `main()` function.

Test actions included:

1. Adding different types of books to the library
2. Displaying the catalog
3. Borrowing books until copies reach zero
4. Attempting to borrow an unavailable book
5. Returning books
6. Searching books by author

Results confirmed that:

* The system correctly updates available copies
* Borrowing fails when stock reaches zero
* Returned books increase stock properly
* Author searches return correct results

---

## 6. Usage Instructions

### Requirements

* Kotlin installed
* IntelliJ IDEA or another Kotlin-compatible IDE

---

### Running the Program

Run the `main()` function located in the package:

```
dam.exer_vl
```

---

### Example Program Flow

1. Books are added to the library.
2. The catalog is displayed.
3. Borrow operations are performed.
4. A return operation is executed.
5. Books are searched by author.

Example console output:

```
===: Library Catalog :===
Title: Kotlin in Action, Author: Dmitry Jemerov, Era: Contemporary, Available: 5 copies
Title: Clean Code, Author: Robert C. Martin, Era: Modern, Available: 3 copies
Title: 1984, Author: George Orwell, Era: Classic, Available: 2 copies
```

The program then demonstrates borrowing and returning books.

