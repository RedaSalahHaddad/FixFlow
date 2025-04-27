package com.example.fixflow.register

import android.app.Application
import android.content.Intent
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.fixflow.hardware.HardwareActivity
import com.example.fixflow.models.AppUser
import com.example.fixflow.receptionist.ReceptionistActivity
import com.example.fixflow.software.SoftwareActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegisterViewModel(application: Application) : AndroidViewModel(application) {

    val firstName = MutableLiveData("")
    val lastName = MutableLiveData("")
    val email = MutableLiveData("")
    val password = MutableLiveData("")
    var selectedRole: String = ""

    fun onRegisterClick() {
        val auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val context = getApplication<Application>().applicationContext

        val emailVal = email.value ?: return
        val passwordVal = password.value ?: return
        val first = firstName.value ?: return
        val last = lastName.value ?: return

        if (selectedRole.isEmpty()) {
            Toast.makeText(context, "Please select a role", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(emailVal, passwordVal).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = AppUser(first, last, emailVal, selectedRole)
                db.collection("users").document(auth.currentUser!!.uid)
                    .set(user)
                    .addOnSuccessListener {
                        Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show()

                        val intent = when (selectedRole) {
                            "receptionist" -> Intent(context, ReceptionistActivity::class.java)
                            "software" -> Intent(context, SoftwareActivity::class.java)
                            "hardware" -> Intent(context, HardwareActivity::class.java)
                            else -> null
                        }
                        intent?.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        context.startActivity(intent)

                    }
                    .addOnFailureListener {
                        Toast.makeText(context, "Firestore Error", Toast.LENGTH_SHORT).show()
                    }
            } else {
                Toast.makeText(context, task.exception?.message ?: "Error", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
