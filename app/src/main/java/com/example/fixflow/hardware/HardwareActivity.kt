package com.example.fixflow.hardware

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import android.app.AlertDialog
import android.widget.ImageButton
import com.example.fixflow.R
import com.example.fixflow.databinding.ActivityHardwareBinding
import com.example.fixflow.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class HardwareActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHardwareBinding
    private val viewModel: HardwareViewModel by viewModels()
    private lateinit var adapter: HardwareRequestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_hardware)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = HardwareRequestsAdapter(
            onMarkUnderMaintenance = { id -> viewModel.updateStatus(id, "Under Maintenance") },
            onMarkDone = { id -> viewModel.updateStatus(id, "Done") }
        )

        binding.hardwareRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.hardwareRecyclerView.adapter = adapter

        viewModel.currentRequest.observe(this) { request ->
            adapter.submitSingleRequest(request)
        }

        viewModel.loadHardwareRequests()

        // إضافة زر الـ Logout
        val logoutIcon: ImageButton = binding.root.findViewById(R.id.logoutIcon)
        logoutIcon.setOnClickListener {
            showAlertDialog()
        }
    }

    private fun showAlertDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Confirm Action")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes") { _, _ ->
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        builder.create().show()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHardwareRequests()
    }
}


