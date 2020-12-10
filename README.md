# LiveEvent
A LiveData for manage events.

## Gradle
Add dependency code to your module's build.gradle file:
```groovy
dependencies {
    implementation 'com.github.champChayangkoon:LiveEvent:0.1.0'
}
```

And add below codes to your project's build.gradle file:
```groovy
allprojects {
	repositories {
        maven { url "https://jitpack.io" }
    }
}
```

## Usage
```kotlin
// MyViewModel
private val _toastMessage = MutableLiveEvent<String>()
val toastMessage: LiveEvent<String> = _toastMessage

fun showToastMessage(toastMessage: String) {
    _toastMessage.setEventValue(toastMessage)
}

// MyActivity
myViewModel.toastMessage.observe(this, EventObserver {
    Toast.makeText(this, it, Toast.LENGTH_LONG).show()
})
```
