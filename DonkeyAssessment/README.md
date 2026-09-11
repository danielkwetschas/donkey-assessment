# Donkey Assessment

Android assessment project built in **Kotlin** using **Android Views**.

The app contains a timeline for users who are members of multiple groups. The timeline is designed to handle a large dataset and loads additional content while the user scrolls. Posts can contain both text and images.

As an extra feature, the app also retrieves current weather data from an external REST API.

## Features

- Timeline with content from multiple groups
- Dataset of 1,000 timeline items
- Pagination in batches of 20 items
- Additional items are loaded automatically while scrolling
- Support for text and image posts
- Current weather information
- Error handling for the weather request
- Unit tests for timeline and weather logic

## Technical choices

The project uses a simple separation between the UI, ViewModels and repositories.
- Kotlin: application language
- Android Views: XML layouts and RecyclerView
- RecyclerView + ListAdapter + DiffUtil: efficient rendering and updating of timeline items
- ViewModel + LiveData: UI state management
- Repository pattern: separates data access from UI logic
- Coil: image loading
- Retrofit + Gson: REST API communication and JSON parsing
- Kotlin Coroutines: asynchronous API calls
- Material Components: UI components

## Timeline

The timeline repository contains 1,000 generated items belonging to several groups. A hardcoded example user is used to simulate group membership. The timeline only shows posts from groups that the current user is a member of.

The example contains five available groups:
- LINK
- Tim & Rank
- Gemeente
- Kerkraad
- Alpha

The hardcoded example user, `Jan`, is a member of three of these groups:
- LINK
- Tim & Rank
- Gemeente

Posts from `Kerkraad` and `Alpha` are therefore filtered out before pagination is applied.

Because the 1,000 generated posts are distributed evenly across the five groups, the example user can access 600 timeline items. The ViewModel requests these items in pages of 20 rather than loading all accessible items into the RecyclerView at once.

When the user approaches the end of the currently loaded list, the next page is requested. This keeps the timeline responsive while demonstrating how a larger dataset can be handled.

Images are included in a selection of posts and are loaded using Coil.

### Changing the hardcoded user groups

For the purpose of this assessment there is no login or backend user management. The current user and group memberships are deliberately hardcoded in `TimeLineRepository`.

The groups visible to the user can be changed by editing the `groups` list of `currentUser`:

```kotlin
private val currentUser = User(
    id = 1,
    name = "Jan",
    groups = listOf(
        "LINK",
        "Tim & Rank",
        "Gemeente"
    )
)
```

For example, adding `"Alpha"` to this list will also allow Alpha posts to appear in the timeline. Removing a group will remove posts from that group from the user's timeline.

## Weather API

The weather card uses the public Open-Meteo API to retrieve the current temperature and weather code for Gouda.

The flow is:

`MainActivity -> WeatherViewModel -> WeatherRepository -> WeatherApi`

Retrofit performs the HTTP request and Gson maps the JSON response to Kotlin data classes. Weather codes are converted to readable descriptions by `WeatherCodeMapper`.
If the request fails, the UI displays that the weather is unavailable.

## Tests

The project contains unit tests covering relevant application logic, including:
- Timeline pagination
- First and subsequent pages
- End-of-dataset behaviour
- Invalid pagination parameters
- Filtering posts based on the current user's group memberships
- Timeline ViewModel behaviour
- Weather code mapping
- Weather repository response handling
- Weather ViewModel success and error states

The timeline tests are based on the included example user being a member of 3 out of 5 groups. With 1,000 evenly distributed generated posts, this means the filtered timeline contains 600 accessible items. Tests for the end of the dataset therefore use 600 as the expected endpoint.

If the hardcoded group memberships are changed, tests that depend on the total number of accessible timeline items must be updated accordingly. For example, with the current data generation each group contains 200 posts, so a user with four groups would have 800 accessible posts.

The tests use JUnit, `InstantTaskExecutorRule` for LiveData and `kotlinx-coroutines-test` for coroutine-based ViewModel tests.

Run the unit tests with:
```bash
./gradlew test
```

On Windows:
```powershell
.\gradlew.bat test
```

## Running the project

1. Clone the repository.
2. Open the project in Android Studio.
3. Allow Gradle to sync the dependencies.
4. Run the app on an Android emulator or physical Android device with internet access.

The project requires a minimum Android SDK version of 24.
No API key or additional configuration is required for the weather API.

## Project structure

```text
app/src/main/
├── java/com/example/donkeyassessment/
│   ├── data/
│   │   ├── TimeLineRepository.kt
│   │   └── weather/
│   ├── ui/
│   │   ├── TimeLineViewModel.kt
│   │   └── WeatherViewModel.kt
│   ├── MainActivity.kt
│   ├── TimeLineAdapter.kt
│   └── TimeLineItem.kt
└── res/
    └── layout/
        ├── activity_main.xml
        └── item_timeline.xml
```

## Notes

The timeline data, user and group memberships are generated or configured locally for the assessment so the application can demonstrate a user-specific timeline and handling a large dataset without requiring authentication or a separate backend. The weather feature was added to demonstrate communication with an external REST API while keeping the scope of the assessment focused.
