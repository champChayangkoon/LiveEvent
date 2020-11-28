package com.chayangkoon.champ.liveevent

import androidx.lifecycle.LiveData

/**
 * A [LiveData] for [Event], simplifying the pattern of use [LiveData] with [Event].
 */
abstract class LiveEvent<T> : LiveData<Event<T>> {
    constructor() : super()
    constructor(value: T) : super(Event(value))

    /**
     * Returns the [Event]'s content, even if it's already been handled.
     */
    val eventValue: T?
        get() = value?.peekContent()
}