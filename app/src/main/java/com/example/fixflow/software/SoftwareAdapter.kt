package com.example.fixflow.software

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fixflow.databinding.TechnicianItemRequestBinding
import com.example.fixflow.models.Request

class SoftwareAdapter(
    private val onUpdateStatus: (Request, String) -> Unit
) : RecyclerView.Adapter<SoftwareAdapter.RequestViewHolder>() {

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
            onUpdateStatus(request, "Under Maintenance")
        }

        holder.binding.doneButton.setOnClickListener {
            onUpdateStatus(request, "Done")
        }
    }

    override fun getItemCount() = requests.size

    fun submitList(list: List<Request>) {
        requests = list
        notifyDataSetChanged()
    }
}
