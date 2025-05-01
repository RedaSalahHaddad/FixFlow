package com.example.fixflow.software

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixflow.R
import com.example.fixflow.databinding.ActivitySoftwareBinding
import com.example.fixflow.models.Request

class SoftwareActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySoftwareBinding
    private val viewModel: SoftwareViewModel by viewModels()
    private lateinit var adapter: SoftwareAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_software)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = SoftwareAdapter(
            onUpdateStatus = { request, newStatus ->
                viewModel.updateStatus(request.id, newStatus)
                Toast.makeText(this, "Status updated", Toast.LENGTH_SHORT).show()
            }
        )

        binding.softwareRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.softwareRecyclerView.adapter = adapter

        viewModel.requests.observe(this) { list ->
            adapter.submitList(list)
        }

        viewModel.loadRequests()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadRequests()
    }
}
