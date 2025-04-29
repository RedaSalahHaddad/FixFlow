package com.example.fixflow.receptionist

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
import com.google.firebase.auth.FirebaseAuth

class ReceptionistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceptionistBinding
    private val viewModel: ReceptionistViewModel by viewModels()
    private lateinit var adapter: RequestsAdapter

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

        // Load Requests
        viewModel.requests.observe(this) { list ->
            adapter.submitList(list)
        }

        viewModel.loadRequests()

        // Navigate to Add Request
        binding.addRequestButton.setOnClickListener {
            startActivity(Intent(this, AddRequestActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadRequests() // Refresh after returning from AddRequest
    }
}
