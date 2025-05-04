package com.example.fixflow.hardware

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixflow.models.Request
import com.google.firebase.firestore.FirebaseFirestore

class HardwareViewModel : ViewModel() {

    private val allRequests = mutableListOf<Request>()
    val currentRequest = MutableLiveData<Request?>()
    private val db = FirebaseFirestore.getInstance()
    private var currentIndex = 0

    fun loadHardwareRequests() {
        db.collection("requests")
            .whereEqualTo("problemType", "Hardware")
            .whereEqualTo("status", "Pending")
            .get()
            .addOnSuccessListener { result ->
                allRequests.clear()
                allRequests.addAll(result.map { it.toObject(Request::class.java) }.sortedBy { it.createdAt })
                currentIndex = 0
                showNextRequest()
            }
    }

    private fun showNextRequest() {
        if (currentIndex < allRequests.size) {
            currentRequest.value = allRequests[currentIndex]
        } else {
            currentRequest.value = null
        }
    }

    fun updateStatus(id: String, newStatus: String) {
        db.collection("requests").document(id)
            .update("status", newStatus)
            .addOnSuccessListener {
                currentIndex++
                showNextRequest()
            }
    }
}
