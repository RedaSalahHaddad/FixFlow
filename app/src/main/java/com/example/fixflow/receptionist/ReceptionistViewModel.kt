package com.example.fixflow.receptionist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReceptionistViewModel : ViewModel() {

    private val _requests = MutableLiveData<List<String>>(emptyList())
    val requests: LiveData<List<String>> = _requests

    private val _userName = MutableLiveData("User") // هيجي من Firebase بعدين
    val userName: LiveData<String> = _userName

    fun loadRequests() {
        _requests.value = listOf("Request 1", "Request 2", "Request 3")
    }

    fun addDummyRequest() {
        val updatedList = _requests.value?.toMutableList() ?: mutableListOf()
        updatedList.add("New Request ${(updatedList.size) + 1}")
        _requests.value = updatedList
    }
}
