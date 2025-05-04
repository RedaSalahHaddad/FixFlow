package com.example.fixflow.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginViewModel : ViewModel() {

    // LiveData للإيميل والباسورد ورسايل الخطأ
    val email = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val emailError = MutableLiveData<String?>()
    val passwordError = MutableLiveData<String?>()

    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun login(callback: (success: Boolean, role: String?, errorMessage: String?) -> Unit) {
        // إعادة تعيين رسايل الخطأ
        emailError.value = null
        passwordError.value = null

        val userEmail = email.value?.trim() ?: ""
        val userPassword = password.value?.trim() ?: ""

        // التحقق من الحقول
        var hasError = false

        if (userEmail.isBlank()) {
            emailError.value = "Enter Email"
            hasError = true
        }

        if (userPassword.isBlank()) {
            passwordError.value = "Enter Password"
            hasError = true
        } else if (userPassword.length < 8) {
            passwordError.value = "Password must be at least 8 characters"
            hasError = true
        }

        if (hasError) {
            return // لو فيه أي خطأ، ما نكملش
        }

        // تسجيل الدخول باستخدام Firebase
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

