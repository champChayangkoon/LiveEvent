package com.chayangkoon.champ.liveevent

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * Gets the value of a [LiveEvent] or waits for it to have one, with a timeout.
 *
 * Use this extension from host-side (JVM) tests. It's recommended to use it alongside
 * `InstantTaskExecutorRule` or a similar mechanism to execute tasks synchronously.
 */
@VisibleForTesting(otherwise = VisibleForTesting.NONE)
fun <T> LiveEvent<T>.getOrAwaitValue(
    time: Long = 2,
    timeUnit: TimeUnit = TimeUnit.SECONDS,
    afterObserve: () -> Unit = {}
): T? {
    var data: T? = null
    val latch = CountDownLatch(1)
    val observer = object : Observer<Event<T>> {
        override fun onChanged(o: Event<T>?) {
            data = o?.getContentIfNotHandled()
            latch.countDown()
            this@getOrAwaitValue.removeObserver(this)
        }
    }
    this.observeForever(observer)

    try {
        afterObserve.invoke()

        // Don't wait indefinitely if the LiveData is not set.
        if (!latch.await(time, timeUnit)) {
            this.removeObserver(observer)
            throw TimeoutException("LiveEvent value was never set.")
        }

    } finally {
        this.removeObserver(observer)
    }
    @Suppress("UNCHECKED_CAST")
    return data
}

/**
 * Observes a [LiveEvent] until the `block` is done executing.
 */
fun <T> LiveEvent<T>.observeForTesting(block: () -> Unit) {
    val observer = Observer<Event<T>> { }
    try {
        observeForever(observer)
        block()
    } finally {
        removeObserver(observer)
    }
}