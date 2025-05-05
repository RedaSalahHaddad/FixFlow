package com.example.fixflow.receptionist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.fixflow.models.Request
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration

class ReceptionistViewModel : ViewModel() {

    val requests = MutableLiveData<List<Request>>()
    private val db = FirebaseFirestore.getInstance()
    private var listener: ListenerRegistration? = null

    init {
        listenToRequests()
    }

    private fun listenToRequests() {
        listener = db.collection("requests")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val list = snapshot.mapNotNull { it.toObject(Request::class.java) }
                        .filter { it.status != "Done" }
                        .sortedBy { it.createdAt }
                    requests.value = list
                }
            }
    }

    fun deleteRequest(id: String) {
        db.collection("requests").document(id)
            .delete()
    }

    override fun onCleared() {
        super.onCleared()
        listener?.remove()
    }
}
