package com.chayangkoon.champ.liveeventexample

import androidx.lifecycle.ViewModel
import com.chayangkoon.champ.liveevent.LiveEvent
import com.chayangkoon.champ.liveevent.MutableLiveEvent

class MainViewModel : ViewModel() {
    private val _toastMessage = MutableLiveEvent<String>()
    val toastMessage: LiveEvent<String> = _toastMessage

    fun showToastMessage(toastMessage: String) {
        _toastMessage.setEventValue(toastMessage)
    }
}