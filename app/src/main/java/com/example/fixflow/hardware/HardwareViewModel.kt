package com.example.fixflow.hardware

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixflow.models.Request
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HardwareViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()
    val currentRequest = MutableLiveData<Request?>()

    fun loadHardwareRequests() {
        val userId = FirebaseAuth.getInstance().currentUser?.uid ?: return

        db.collection("requests")
            .whereEqualTo("problemType", "Hardware")
            .whereEqualTo("status", "Pending")
            .get()
            .addOnSuccessListener { result ->
                val sortedRequests = result.map { it.toObject(Request::class.java) }
                    .sortedBy { it.createdAt }

                if (sortedRequests.isNotEmpty()) {
                    val userIndex = userId.hashCode().let { if (it < 0) -it else it } % sortedRequests.size
                    currentRequest.value = sortedRequests[userIndex]
                } else {
                    currentRequest.value = null
                }
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
