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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_receptionist)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val userNameFromLogin = intent.getStringExtra("username") ?: "User"
        viewModel.userName.value = userNameFromLogin

        setupRecyclerView()

        binding.addRequestButton.setOnClickListener {
            // فتح صفحة إضافة طلب جديد
        }
    }

    private fun setupRecyclerView() {
        val adapter = RepairsAdapter(viewModel.repairs.value ?: listOf())
        binding.repairsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.repairsRecyclerView.adapter = adapter
    }
}
