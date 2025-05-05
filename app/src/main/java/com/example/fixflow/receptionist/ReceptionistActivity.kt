package com.example.fixflow.receptionist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixflow.R
import com.example.fixflow.addrequest.AddRequestActivity
import com.example.fixflow.databinding.ActivityReceptionistBinding
import com.example.fixflow.login.LoginActivity
import com.example.fixflow.receptionist.done.DoneRequestsActivity
import com.google.firebase.auth.FirebaseAuth

class ReceptionistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceptionistBinding
    private val viewModel: ReceptionistViewModel by viewModels()
    private lateinit var adapter: RequestsAdapter
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_receptionist)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = RequestsAdapter { requestId ->
            viewModel.deleteRequest(requestId)
            Toast.makeText(this, "Request deleted", Toast.LENGTH_SHORT).show()
        }

        binding.requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.requestsRecyclerView.adapter = adapter

        viewModel.requests.observe(this) { list ->
            adapter.submitList(list)
        }

        binding.addRequestButton.setOnClickListener {
            startActivity(Intent(this, AddRequestActivity::class.java))
        }

        binding.finishButton.setOnClickListener {
            startActivity(Intent(this, DoneRequestsActivity::class.java))
        }

        binding.logoutIcon.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes") { _, _ ->
                    firebaseAuth.signOut()
                    Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, LoginActivity::class.java))
                    finish()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}
