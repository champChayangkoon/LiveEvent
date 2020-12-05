package com.chayangkoon.champ.liveevent

/**
 * A [androidx.lifecycle.MutableLiveData] for [Event],
 * simplifying the pattern of use [androidx.lifecycle.MutableLiveData] with [Event].
 */
class MutableLiveEvent<T> : LiveEvent<T> {
    constructor() : super()
    constructor(value: T) : super(value)

    public override fun setEventValue(value: T) {
        super.setEventValue(value)
    }

    public override fun postEventValue(value: T) {
        super.postEventValue(value)
    }
}