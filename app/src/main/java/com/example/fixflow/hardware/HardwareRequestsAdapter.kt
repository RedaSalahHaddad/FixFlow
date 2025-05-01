package com.example.fixflow.hardware

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fixflow.databinding.TechnicianItemRequestBinding
import com.example.fixflow.models.Request

class HardwareRequestsAdapter(
    private val onMarkUnderMaintenance: (String) -> Unit,
    private val onMarkDone: (String) -> Unit
) : RecyclerView.Adapter<HardwareRequestsAdapter.RequestViewHolder>() {

    private var requests: List<Request> = listOf()

    inner class RequestViewHolder(val binding: TechnicianItemRequestBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = TechnicianItemRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        holder.binding.request = request

        holder.binding.underMaintenanceButton.setOnClickListener {
            onMarkUnderMaintenance(request.id)
        }

        holder.binding.doneButton.setOnClickListener {
            onMarkDone(request.id)
        }
    }

    override fun getItemCount() = requests.size

    fun submitList(list: List<Request>) {
        requests = list
        notifyDataSetChanged()
    }
}
