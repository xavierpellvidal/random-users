
## 📱 Overview

An Android application that displays a list of random users fetched from an external API. Features a modern and intuitive UI built with Jetpack Compose, allowing users to filter, delete, and manage profiles.

## ✨ Features

- **User List**: Browse through random user profiles with detailed information
- **Filtering**: Filter users by various criteria
- **Local Storage**: Access previously loaded users while offline
- **Reactive UI**: Real-time updates when data changes

## 🛠️ Tech Stack

| Category | Technologies |
|----------|--------------|
| **Core** | Kotlin, Coroutines, Flow |
| **UI** | Jetpack Compose |
| **Architecture** | Clean Architecture, MVI Pattern |
| **Dependency Injection** | Hilt |
| **Networking** | Retrofit |
| **Local Storage** | Room |
| **Functional Programming** | Arrow |
| **Testing** | Turbine, Mockk, Roborazzi, Robolectric |
| **Planned** | MockWebServer |

## 🏗️ Architecture

The project follows **Clean Architecture** principles with an MVI (Model-View-Intent) pattern:

- **Presentation Layer**: Compose UI components and ViewModels
- **Domain Layer**: Business logic encapsulated in UseCases
- **Data Layer**: Repository implementations and data sources
- **Core Module**: Shared utilities and reusable components

```
app/
├── core/            # Core utilities and extensions
├── data/            # Data layer implementations
├── domain/          # Business logic and use cases
└── presentation/    # UI components and ViewModels
```

## 🧪 Testing

- **Unit Tests**: Cover ViewModels, data and parts of the core layers
- **UI Tests**: Screenshot testing with Roborazzi implemented
- **Integration Tests**: Planned for UseCase to DataSource layers using MockWebServer and Room

## 🚀 Future Improvements

- Add integration tests for UseCase to DataSource layers using MockWebServer
- Expand test coverage across the application

## 🔧 Getting Started

1. Clone the repository
   ```bash
   git clone https://github.com/yourusername/random-users.git
   ```
2. Open the project in Android Studio
3. Sync the project with Gradle
4. Run the app on an emulator or physical device


## 📝 License

This project is licensed under the MIT License - see the LICENSE file for details
```
