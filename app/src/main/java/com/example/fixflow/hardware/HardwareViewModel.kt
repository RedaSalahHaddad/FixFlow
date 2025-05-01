package com.example.fixflow.hardware

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixflow.models.Request
import com.google.firebase.firestore.FirebaseFirestore

class HardwareViewModel : ViewModel() {

    val requests = MutableLiveData<List<Request>>()
    private val db = FirebaseFirestore.getInstance()

    fun loadHardwareRequests() {
        db.collection("requests")
            .whereEqualTo("problemType", "Hardware")
            .get()
            .addOnSuccessListener { result ->
                val list = result.map { it.toObject(Request::class.java) }.sortedBy { it.id }
                requests.value = list
            }
    }

    fun updateStatus(id: String, newStatus: String) {
        db.collection("requests").document(id)
            .update("status", newStatus)
            .addOnSuccessListener {
                loadHardwareRequests()
            }
    }
}
