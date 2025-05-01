package com.example.fixflow.hardware

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixflow.R
import com.example.fixflow.databinding.ActivityHardwareBinding

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

        viewModel.requests.observe(this) { list ->
            adapter.submitList(list)
        }

        viewModel.loadHardwareRequests()
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadHardwareRequests()
    }
}
