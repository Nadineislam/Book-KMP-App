# 📚 Book App

This app browses through list of books fetched from a remote API, search for specific titles, and view detailed information with ease. Enjoy a seamless experience with favorites management through local storage — all powered by modern Android technologies.

# 🧩 Features

* Book List Screen: Displays a dynamic list of books fetched from the API.

* Book Details Screen: View detailed information for any selected book.

* Favorites: Mark books as favorites and store them locally using Room Database.

* Search: Search for specific books from the API with instant results.

* Offline Support: Access favorite books even when offline.

* Clean & Reactive UI: Built entirely with Jetpack Compose for a modern look and smooth interaction.

# 🧠 Libraries and Technologies Used
🔹 Ktor : Used for making network requests to fetch book data from the REST API. Provides a lightweight and asynchronous HTTP client for smooth and efficient data communication.

🔹 Koin : A lightweight dependency injection framework for Kotlin, used to manage dependencies across ViewModels, repositories, and data sources.

🔹 Room Database : Provides an abstraction layer over SQLite for managing local data storage. Used to persist favorite books and retrieve them offline.

🔹 Jetpack Compose : Modern declarative UI toolkit for building reactive and maintainable Android interfaces.

🔹 Navigation (Compose Navigation) : Used for seamless navigation between screens — from the list of books to their detailed views.

🔹 Coroutines : Handles asynchronous operations like network calls and database interactions, ensuring smooth and responsive UI updates.

🔹 Clean Architecture : Implements a clear separation of concerns with data, domain, and presentation layers, promoting scalability and testability.

🔹 MVI (Model–View–Intent) : Ensures a unidirectional data flow where the UI renders based on a single immutable state, making state management predictable and robust.
