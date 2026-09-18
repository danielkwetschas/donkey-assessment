# Donkey Assessment

A simple Android application showcasing a timeline with infinite scrolling. 

## Overview

The application displays a feed of messages from various groups in a church community context. 
These messages are filtered to be exclusively from groups which the user is a member of (an example user is hardcoded).

## Features

- **Timeline Feed:** Displays a list of entries with sender names, group categories, text content, and optional images.
- **Infinite Scrolling:** Automatically loads more entries as the user reaches the end of the current list, ensuring a smooth browsing experience.
- **Group Filtering:** Simulates user-specific feeds by filtering entries based on the groups the current user belongs to.
- **MVVM Architecture:** Separation of concerns using `ViewModel` and `LiveData` for a clean and testable codebase.
- **Unit Testing:** Includes unit tests using Robolectric and Android Arch Core Testing.

## Tech Stack

- **Language:** Kotlin
- **UI Framework:** Android Views (RecyclerView, CardView)
- **Architecture:** MVVM (Model-View-ViewModel)
- **Image Loading:** [Coil 3](https://coil-kt.github.io/coil/)
- **Testing:** JUnit, Robolectric

## Testing

The project includes unit tests:

| Class | What it covers |
|---|---|
| `TimeLineRepositoryTest` | Pagination bounds, negative-input handling, group filtering |
| `TimelineViewModelTest` | First page (auto-)loads once, pages append without duplicates, stops calling the repository once data is exhausted |
| `TimeLineDiffCallbackTest` | `areItemsTheSame` / `areContentsTheSame` logic |
| `TimeLineAdapterTest` | `submitList` updates item count, replaces (not appends) the list, empty list clears it |

**Why Robolectric for the adapter test:** `ListAdapter` pulls in `AsyncListDiffer`, which
initializes a `Looper.getMainLooper()`-backed executor in a static initializer. Plain JVM unit
tests don't have a real Android runtime, so that call throws. Robolectric provides a working
`Looper`/`Handler` shadow on the JVM, letting `TimeLineAdapter` be instantiated and exercised
without needing an emulator or full instrumented test.

`TimeLineAdapterTest` also uses  a `CountDownLatch`rather than asserting immediately after 
`submitList()`, since `AsyncListDiffer` computes diffs on a background executor - asserting 
without waiting on the callback would be a race condition.

### Run tests

```bash
./gradlew test
```

## Project structure

```
app/src/main/java/com/hoijesse/donkeyassessment/
├── data/
│   ├── TimeLineEntry.kt       
│   ├── TimeLineRepository.kt                
│   └── User.kt  
├── ui/
│   ├── MainActivity.kt        
│   ├── TimeLineAdapter.kt     
│   └── TimelineViewModel.kt   
```

## Running the project

1. Clone the repository
2. Open the project in Android Studio.
3. Let Gradle sync.
4. Run the app on an emulator or device (min SDK 24, internet access required).
