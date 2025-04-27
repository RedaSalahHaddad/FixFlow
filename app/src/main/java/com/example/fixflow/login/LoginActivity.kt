package com.example.fixflow.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fixflow.R
import com.example.fixflow.databinding.ActivityLoginBinding
import com.example.fixflow.hardware.HardwareActivity
import com.example.fixflow.receptionist.ReceptionistActivity
import com.example.fixflow.software.SoftwareActivity
import com.example.fixflow.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        binding.loginButton.setOnClickListener {
            viewModel.login { success, role, errorMessage ->
                if (success) {
                    // نأخذ اليوزر نيم من الإيميل (أو من ViewModel إذا غيرت)
                    val username = binding.emailEditText.text.toString().substringBefore("@")

                    // ننشئ Intent حسب الدور ثم نضيف extra للـ username
                    val intent = when (role) {
                        "receptionist" -> Intent(this, ReceptionistActivity::class.java)
                        "software"     -> Intent(this, SoftwareActivity::class.java)
                        "hardware"     -> Intent(this, HardwareActivity::class.java)
                        else           -> null
                    }
                    intent?.putExtra("username", username)
                    intent?.let {
                        startActivity(it)
                        finish()
                    }
                } else {
                    Toast.makeText(this, errorMessage ?: "Login Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.goToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}
