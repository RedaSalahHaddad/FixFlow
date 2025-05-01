package com.example.fixflow.addrequest

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.fixflow.R
import com.example.fixflow.databinding.ActivityAddRequestBinding

class AddRequestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddRequestBinding
    private val viewModel: AddRequestViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_request)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel


        // Dropdown setup
        val problemTypes = listOf("Software", "Hardware")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, problemTypes)
        binding.problemTypeSpinner.adapter = adapter

        binding.submitButton.setOnClickListener {
            viewModel.problemType.value = binding.problemTypeSpinner.selectedItem.toString()

            viewModel.submitRequest { success, message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                if (success) {
                    finish()
                }
            }
        }
    }
}
