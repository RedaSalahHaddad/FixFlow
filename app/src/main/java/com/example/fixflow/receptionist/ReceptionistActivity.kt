package com.example.fixflow.receptionist

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixflow.R
import com.example.fixflow.databinding.ActivityReceptionistBinding

class ReceptionistActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReceptionistBinding
    private val viewModel: ReceptionistViewModel by viewModels()
    private lateinit var adapter: RequestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_receptionist)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        setupRecyclerView()
        observeRequests()

        binding.addRequestButton.setOnClickListener {
            viewModel.addDummyRequest()
        }
    }

    private fun setupRecyclerView() {
        adapter = RequestsAdapter()
        binding.requestsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.requestsRecyclerView.adapter = adapter
    }

    private fun observeRequests() {
        viewModel.requests.observe(this) { list ->
            adapter.submitList(list.toList())
        }
    }
}
