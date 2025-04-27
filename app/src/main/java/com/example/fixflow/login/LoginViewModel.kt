package com.example.fixflow.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun login(callback: (success: Boolean, role: String?, errorMessage: String?) -> Unit) {
        val userEmail = email.value ?: ""
        val userPassword = password.value ?: ""

        if (userEmail.isBlank() || userPassword.isBlank()) {
            callback(false, null, "Please fill in all fields")
            return
        }

        auth.signInWithEmailAndPassword(userEmail, userPassword)
            .addOnSuccessListener { authResult ->
                val uid = authResult.user?.uid ?: return@addOnSuccessListener
                db.collection("users").document(uid).get()
                    .addOnSuccessListener { document ->
                        if (document.exists()) {
                            val role = document.getString("role")?.lowercase()
                            callback(true, role, null)
                        } else {
                            callback(false, null, "User data not found")
                        }
                    }
                    .addOnFailureListener {
                        callback(false, null, "Error retrieving user data")
                    }
            }
            .addOnFailureListener {
                callback(false, null, "Login failed: ${it.message}")
            }
    }
}
