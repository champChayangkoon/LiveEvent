package com.chayangkoon.champ.liveevent

/**
 * A [androidx.lifecycle.MutableLiveData] for [Event],
 * simplifying the pattern of use [androidx.lifecycle.MutableLiveData] with [Event].
 */

class MutableLiveEvent<T> : LiveEvent<T> {
    constructor() : super()
    constructor(value: T) : super(value)

    /**
     * Sets the [Event]'s content. If there are active observers,
     * the [Event]'s content will be dispatched to them.
     * @see [androidx.lifecycle.LiveData.setValue]
     */
    fun setEventValue(value: T) {
        setValue(Event(value))
    }

    /**
     * Posts a task to a main thread to set the given [Event]'s content.
     * @see [androidx.lifecycle.LiveData.postValue]
     */
    fun postEventValue(value: T) {
        postValue(Event(value))
    }
}