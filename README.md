
## 📱 Overview

An Android application that displays a list of random users fetched from an external API. Features a modern and intuitive UI built with Jetpack Compose, allowing users to filter and delete profiles.


## ✨ Features

- **User List**: Browse through random user profiles with detailed information. Infinite scroll implemented
- **USer details**: View detailed information about each user
- **Filtering**: Filter users by name, surname and email
- **Delete**: Remove users from the list


## 🛠️ Tech Stack

| Category | Technologies                                          |
|----------|-------------------------------------------------------|
| **Core** | Kotlin, Coroutines, Flow                              |
| **UI** | Jetpack Compose                                       |
| **Architecture** | Clean Architecture, MVI Pattern                       |
| **Dependency Injection** | Hilt                                                  |
| **Networking** | Retrofit                                              |
| **Local Storage** | Room, SharedPreferences                               |
| **Functional Programming** | Arrow                                                 |
| **Testing** | Turbine, Mockk, Roborazzi, Robolectric, MockWebServer |


## 🏗️ Architecture

The project follows **Clean Architecture** principles with an MVI (Model-View-Intent) pattern:

- **Presentation Layer**: Compose UI components and ViewModels modularized by feature
- **Domain Layer**: Business logic encapsulated in UseCases and repositories
- **Data Layer**: Repository implementations and data sources
- **Core Module**: Shared utilities and reusable components like api, database and preferences

```
app/
├── core/            # Core modules
├── data/            # Data layer
├── domain/          # Domain layer
└── presentation/    # Feature modules
    └── users/       # User list feature
```


## 🧪 Testing

- **Unit Tests**: Cover ViewModels, usecases and data layer
- **UI Tests**: Screenshot testing with Roborazzi implemented
- **Integration Tests**: VM integration tests with mockwebserver


## 🚀 Future Improvements

- Manage strings in core-presentation module
- Expand test coverage across the application
- Integration tests from compose to data layer
- Compose navigation tests
- Github Actions for CI/CD


## 🔧 Getting Started

1. Clone the repository
   ```bash
   git clone https://github.com/xavierpellvidal/random-users.git
   ```
2. Open the project in Android Studio
3. Sync the project with Gradle
4. Run the app on an emulator or physical device


## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details
