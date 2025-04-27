package com.example.fixflow.register

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fixflow.R
import com.example.fixflow.databinding.ActivityRegisterBinding
import com.example.fixflow.login.LoginActivity

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // اختيار الدور
        binding.roleGroup.setOnCheckedChangeListener { _, checkedId ->
            viewModel.selectedRole = when (checkedId) {
                R.id.roleReceptionist -> "receptionist"
                R.id.roleSoftware     -> "software"
                R.id.roleHardware     -> "hardware"
                else                  -> ""
            }
        }

        // زر إنشاء الحساب
        binding.createAccount.setOnClickListener {
            viewModel.onRegisterClick()
        }

        // رابط للـ Login
        binding.goToLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
