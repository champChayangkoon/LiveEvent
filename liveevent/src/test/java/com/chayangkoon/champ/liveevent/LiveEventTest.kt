package com.chayangkoon.champ.liveevent

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import io.mockk.verify
import org.junit.Rule
import org.junit.Test

class LiveEventTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun testMutableLiveEventReturnContentOnce() {
        val eventData = MutableLiveEvent<String>()

        eventData.setEventValue("Hi MutableLiveEvent")

        assert(eventData.getOrAwaitValue() == "Hi MutableLiveEvent")
        assert(eventData.getOrAwaitValue() == null)
    }

    @Test
    fun testMutableLiveEventReturnContentOnceWithPost() {
        val eventData = MutableLiveEvent<String>()

        eventData.postEventValue("Hi MutableLiveEvent")

        assert(eventData.getOrAwaitValue() == "Hi MutableLiveEvent")
        assert(eventData.getOrAwaitValue() == null)
    }

    @Test
    fun testMutableLiveEventReturnContentOnceWithConstructor() {
        val eventData = MutableLiveEvent("Hi MutableLiveEvent")

        assert(eventData.getOrAwaitValue() == "Hi MutableLiveEvent")
        assert(eventData.getOrAwaitValue() == null)
    }

    @Test
    fun testLiveEventReturnContentOnce() {
        val mutableEventData = MutableLiveEvent<String>()
        val eventData: LiveEvent<String> = mutableEventData

        mutableEventData.setEventValue("Hi LiveEvent")

        assert(eventData.getOrAwaitValue() == "Hi LiveEvent")
        assert(eventData.getOrAwaitValue() == null)
    }

    @Test
    fun testLiveEventReturnContentOnceWithPost() {
        val mutableEventData = MutableLiveEvent<String>()
        val eventData: LiveEvent<String> = mutableEventData

        mutableEventData.postEventValue("Hi LiveEvent")

        assert(eventData.getOrAwaitValue() == "Hi LiveEvent")
        assert(eventData.getOrAwaitValue() == null)
    }

    @Test
    fun testLiveEventReturnContentOnceWithConstructor() {
        val mutableEventData = MutableLiveEvent("Hi LiveEvent")
        val eventData: LiveEvent<String> = mutableEventData

        assert(eventData.getOrAwaitValue() == "Hi LiveEvent")
        assert(eventData.getOrAwaitValue() == null)
    }

    @Test
    fun testEventObserverObserveContent() {
        val eventData = MutableLiveEvent<String>()
        val eventObserver = EventObserver<String> {
            assert(it == "Hi EventObserver")
        }
        eventData.observeForever(eventObserver)
        eventData.setEventValue("Hi EventObserver")
        eventData.removeObserver(eventObserver)
    }

    @Test
    fun testEventObserverObserveContentOnce() {
        val mockOnEventUnhandledContentFirst = mockk<(String) -> Unit>(relaxed = true)
        val mockOnEventUnhandledContentSecond = mockk<(String) -> Unit>(relaxed = true)
        val eventObserverFirst = EventObserver(mockOnEventUnhandledContentFirst)
        val eventObserverSecond =  EventObserver(mockOnEventUnhandledContentSecond)
        val eventData = MutableLiveEvent<String>()
        eventData.observeForever(eventObserverFirst)
        eventData.observeForever(eventObserverSecond)

        eventData.setEventValue("Hi EventObserver")

        verify(exactly = 1) { mockOnEventUnhandledContentFirst.invoke("Hi EventObserver") }
        verify(exactly = 0) { mockOnEventUnhandledContentSecond.invoke("Hi EventObserver") }
        eventData.removeObserver(eventObserverFirst)
        eventData.removeObserver(eventObserverSecond)
    }

    @Test
    fun testPeekContentAfterContentIsAlreadyHandled() {
        val eventData = MutableLiveEvent<String>()
        val eventObserver = EventObserver<String> {}
        eventData.observeForever(eventObserver)

        eventData.setEventValue("Hi EventObserver")

        assert(eventData.eventValue == "Hi EventObserver")
        eventData.removeObserver(eventObserver)
    }
}