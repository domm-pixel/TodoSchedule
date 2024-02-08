package com.dom.todo.base

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dom.todo.util.Event
import kotlinx.coroutines.*

open class BaseViewModel: ViewModel() {

    protected val TAG = this.javaClass.name

    private val _callbackEvent = MutableLiveData<Event<HashMap<String, Bundle?>>>()
    val callbackEvent: LiveData<Event<HashMap<String,Bundle?>>> = _callbackEvent

    protected val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }

    fun onCallbackEvent(event: String, bundle: Bundle? = null) {
        _callbackEvent.value = Event(hashMapOf(event to bundle))
    }
}