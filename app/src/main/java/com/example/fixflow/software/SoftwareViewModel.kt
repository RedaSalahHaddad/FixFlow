package com.example.fixflow.software

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixflow.models.Request
import com.google.firebase.firestore.FirebaseFirestore

class SoftwareViewModel : ViewModel() {

    val requests = MutableLiveData<List<Request>>()
    private val db = FirebaseFirestore.getInstance()

    fun loadRequests() {
        db.collection("requests")
            .whereEqualTo("problemType", "Software")
            .orderBy("id") // assuming older requests come first by id
            .get()
            .addOnSuccessListener { result ->
                val list = result.map { it.toObject(Request::class.java) }
                requests.value = list
            }
    }

    fun updateStatus(id: String, newStatus: String) {
        db.collection("requests").document(id)
            .update("status", newStatus)
            .addOnSuccessListener { loadRequests() }
    }
}
