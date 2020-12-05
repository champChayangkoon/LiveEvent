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
    val eventValue: T? get() = super.getValue()?.peekContent()

    /**
     * Sets the [Event]'s content. If there are active observers,
     * the [Event]'s content will be dispatched to them.
     * @see [androidx.lifecycle.LiveData.setValue]
     */
    protected open fun setEventValue(value: T) {
        super.setValue(Event(value))
    }

    /**
     * Posts a task to a main thread to set the given [Event]'s content.
     * @see [androidx.lifecycle.LiveData.postValue]
     */
    protected open fun postEventValue(value: T) {
        super.postValue(Event(value))
    }
}