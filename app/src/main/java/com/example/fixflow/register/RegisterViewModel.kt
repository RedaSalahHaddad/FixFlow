package com.example.fixflow.register

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fixflow.models.AppUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel(application: Application) : AndroidViewModel(application) {


    val firstName = MutableLiveData("")
    val lastName = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    val firstNameError = MutableLiveData<String?>()
    val lastNameError = MutableLiveData<String?>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()
    val roleError = MutableLiveData<String?>()

    var selectedRole: String = ""

    fun onRegisterClick(callback: (success: Boolean, role: String?, errorMessage: String?) -> Unit) {

        firstNameError.value = null
        lastNameError.value = null
        emailError.value = null
        passwordError.value = null
        roleError.value = null

        val first = firstName.value?.trim() ?: ""
        val last = lastName.value?.trim() ?: ""
        val emailVal = email.value?.trim() ?: ""
        val passwordVal = password.value?.trim() ?: ""


        var hasError = false

        if (first.isBlank()) {
            firstNameError.value = "Enter First Name"
            hasError = true
        }

        if (last.isBlank()) {
            lastNameError.value = "Enter Last Name"
            hasError = true
        }

        if (emailVal.isBlank()) {
            emailError.value = "Enter Email"
            hasError = true
        }

        if (passwordVal.isBlank()) {
            passwordError.value = "Enter Password"
            hasError = true
        } else if (passwordVal.length < 8) {
            passwordError.value = "Password must be at least 8 characters"
            hasError = true
        }

        if (selectedRole.isEmpty()) {
            roleError.value = "Select a Role"
            hasError = true
        }

        if (hasError) {
            return
        }

        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()


        auth.createUserWithEmailAndPassword(emailVal, passwordVal).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = AppUser(first, last, emailVal, selectedRole)
                db.collection("users").document(auth.currentUser!!.uid)
                    .set(user)
                    .addOnSuccessListener {
                        callback(true, selectedRole, null)
                    }
                    .addOnFailureListener {
                        callback(false, null, "Firestore Error")
                    }
            } else {
                callback(false, null, task.exception?.message ?: "Error")
            }
        }
    }
}