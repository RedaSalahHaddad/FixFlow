package com.example.fixflow.receptionist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixflow.models.Request
import com.google.firebase.firestore.FirebaseFirestore

class ReceptionistViewModel : ViewModel() {

    val requests = MutableLiveData<List<Request>>()
    private val db = FirebaseFirestore.getInstance()

    fun loadRequests() {
        db.collection("requests")
            .get()
            .addOnSuccessListener { result ->
                val list = result.map { it.toObject(Request::class.java) }
                requests.value = list
            }
    }

    fun deleteRequest(id: String) {
        db.collection("requests").document(id)
            .delete()
            .addOnSuccessListener {
                loadRequests()
            }
    }
}
