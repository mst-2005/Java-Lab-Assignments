# Java Lab Assignments ☕

A comprehensive repository documenting a technical journey in Java programming—from foundational class design to advanced event-driven GUI applications. This collection serves as a systematic record of laboratory experiments focused on software architecture and Object-Oriented principles.

---

## 🚀 The Learning Roadmap

This repository is structured to demonstrate the transition from procedural logic to sophisticated software design.

1.  **Phase I: Object-Oriented Foundations** 🧱
    *   Defining Classes, managing state/behavior, and implementing core pillars like Inheritance and Polymorphism.
2.  **Phase II: Robustness & Data Management** 🛡️
    *   Implementing custom exception handling and utilizing the Java Collections Framework for scalable data storage.
3.  **Phase III: Enterprise Integration** 🏗️
    *   Connecting applications to persistent storage using MySQL and JDBC for real-world data management.
4.  **Phase IV: Modern GUI Development** 🎨
    *   Building responsive, interactive user interfaces with **JavaFX**, focusing on event-driven programming.

---

## 📑 List of Experiments

The following table summarizes the key milestones and problem statements implemented in this repository:

| Exp. No | Project Title | Problem Statement & Key Concepts |
| :--- | :--- | :--- |
| **01** | **Menu-Driven Calculator** | Arithmetic operations with modular methods and input validation logic. |
| **02** | **Vehicle Management System** | Class attributes/methods and managing multiple objects using arrays. |
| **03** | **Book Inventory System** | Record management using `ArrayList`, constructors, and Exception Handling. |
| **04** | **Vector Operations Program** | Implementation of 2D/3D vector algebra with robust error handling. |
| **05** | **Banking Application** | Inheritance-based system featuring account types and method overriding. |
| **06** | **Employee Payroll System** | Demonstrating single, multilevel, and hierarchical inheritance patterns. |
| **07** | **Student Record Manager** | File-based CRUD application focusing on data persistence and I/O. |
| **08** | **Collections & Patterns** | Application of Java Collections Framework and basic Design Patterns. |
| **09** | **JDBC Database App** | Full-stack connectivity using Java to perform SQL CRUD operations. |
| **10** | **JavaFX GUI Application** | Modern UI controls and event handling integrated with backend logic. |

---

## 🛠️ Technology Stack

- **Language**: Java 17+
- **Database**: MySQL 8.0 (for JDBC experiments)
- **GUI Framework**: JavaFX 21 (with CSS for custom theming)
- **Build Tool**: Maven / Gradle (for dependency management)

---

## 🔧 Setup & Execution

### Prerequisites
*   **JDK 17** or higher.
*   **IDE**: IntelliJ IDEA, Eclipse, or VS Code with Java Extension Pack.
*   **MySQL Server**: Required for Experiment 09.

### Running an Experiment
Navigate to the specific experiment directory and compile the source files. For JavaFX modules, ensure the module path is correctly set:
```bash
# Compilation example for JavaFX
javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml YourApp.java

# Execution example
java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml YourApp
