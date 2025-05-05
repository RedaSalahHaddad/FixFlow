package com.example.fixflow.register

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fixflow.R
import com.example.fixflow.databinding.ActivityRegisterBinding
import com.example.fixflow.hardware.HardwareActivity
import com.example.fixflow.login.LoginActivity
import com.example.fixflow.receptionist.ReceptionistActivity
import com.example.fixflow.software.SoftwareActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        binding.roleGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.selectedRole = when (checkedId) {
                R.id.roleReceptionist -> "receptionist"
                R.id.roleSoftware -> "software"
                R.id.roleHardware -> "hardware"
                else -> ""
            }
        }

        // مراقبة رسايل الخطأ لتحريك الـ focus
        viewModel.firstNameError.observe(this) { error ->
            if (!error.isNullOrBlank()) {
                binding.firstName.requestFocus()
            }
        }
        viewModel.lastNameError.observe(this) { error ->
            if (!error.isNullOrBlank() && viewModel.firstNameError.value.isNullOrBlank()) {
                binding.lastName.requestFocus()
            }
        }
        viewModel.emailError.observe(this) { error ->
            if (!error.isNullOrBlank() && viewModel.firstNameError.value.isNullOrBlank() && viewModel.lastNameError.value.isNullOrBlank()) {
                binding.email.requestFocus()
            }
        }
        viewModel.passwordError.observe(this) { error ->
            if (!error.isNullOrBlank() && viewModel.firstNameError.value.isNullOrBlank() && viewModel.lastNameError.value.isNullOrBlank() && viewModel.emailError.value.isNullOrBlank()) {
                binding.password.requestFocus()
            }
        }


        binding.createAccount.setOnClickListener {
            viewModel.onRegisterClick { success, role, errorMessage ->
                if (success) {
                    val username = viewModel.email.value?.substringBefore("@") ?: "User"
                    val intent = when (role) {
                        "receptionist" -> Intent(this, ReceptionistActivity::class.java)
                        "software" -> Intent(this, SoftwareActivity::class.java)
                        "hardware" -> Intent(this, HardwareActivity::class.java)
                        else -> null
                    }
                    intent?.putExtra("username", username)
                    intent?.let {
                        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(it)
                        Log.d("RegisterActivity", "Finishing RegisterActivity")
                        finish()
                    }
                } else if (errorMessage != null) {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }
        }


        binding.goToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}