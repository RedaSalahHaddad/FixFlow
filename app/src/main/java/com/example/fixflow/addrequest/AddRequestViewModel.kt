package com.example.fixflow.addrequest

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixflow.models.Request
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

class AddRequestViewModel : ViewModel() {

    val clientName = MutableLiveData<String>()
    val deviceType = MutableLiveData<String>()
    val problemDescription = MutableLiveData<String>()
    val problemType = MutableLiveData<String>()

    private val db = FirebaseFirestore.getInstance()

    fun submitRequest(callback: (Boolean, String) -> Unit) {
        val id = UUID.randomUUID().toString()
        val newRequest = Request(
            id = id,
            clientName = clientName.value ?: "",
            deviceType = deviceType.value ?: "",
            problemDescription = problemDescription.value ?: "",
            problemType = problemType.value ?: ""
        )

        db.collection("requests")
            .document(id)
            .set(newRequest)
            .addOnSuccessListener {
                callback(true, "Request added successfully")
            }
            .addOnFailureListener { e ->
                callback(false, e.message ?: "An error occurred")
            }
    }
}
