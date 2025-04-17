# Random Users

## Description

This project is an Android application that displays a list of random users fetched from an API. Users can be filtered, deleted, and managed through a clean and modern UI built with Jetpack Compose.

## Tech Stack

- **Kotlin**: Main programming language.
- **Coroutines & Flow**: For asynchronous programming and reactive data streams.
- **Jetpack Compose**: Declarative UI framework.
- **Hilt**: Dependency injection.
- **Retrofit**: HTTP client for API communication.
- **Arrow**: Functional programming utilities.
- **Room**: Local database for offline storage.
- **Turbine**: Testing reactive flows.
- **Mockk**: Mocking library for unit tests.
- **Roborazzi**: Screenshot testing.
- **Robolectric**: Unit testing in a simulated Android environment.
- (Planned) **MockWebServer**: For integration tests of network layers.

## Architecture

The project follows a **Clean Architecture** approach with an MVI (Model-View-Intent) pattern:

- **Presentation Layer**: Contains UI components and `ViewModel`s.
- **Domain Layer**: Business logic with `UseCase`s.
- **Data Layer**: Handles data sources and repository implementation.
- **Core Module**: Shared utilities and reusable components.

## Testing

- **Unit Tests**: Cover `ViewModel`s.
- **Integration Tests**: Planned for `UseCase` to `DataSource` layers using `MockWebServer` and `Room`.
- (Planned) **UI Tests**: Screenshot testing with `Roborazzi`.

### Future Improvements

- Implement **SharedFlow** to handle and consume errors across the app.
- Add **integration tests** for `UseCase` to `DataSource` layers.
- Fix **screenshot tests**
- Expand **unit tests** for the `data` and `core` layers.

## Setup

1. Clone the repository.
2. Open the project in Android Studio.
3. Sync the project with Gradle.
4. Run the app on an emulator or physical device.
