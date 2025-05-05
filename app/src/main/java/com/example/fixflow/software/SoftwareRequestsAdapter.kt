package com.example.fixflow.software

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fixflow.databinding.TechnicianItemRequestBinding
import com.example.fixflow.models.Request

class SoftwareRequestsAdapter(
    private val onMarkUnderMaintenance: (String) -> Unit,
    private val onMarkDone: (String) -> Unit
) : RecyclerView.Adapter<SoftwareRequestsAdapter.RequestViewHolder>() {

    private var currentRequest: Request? = null

    inner class RequestViewHolder(val binding: TechnicianItemRequestBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = TechnicianItemRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        currentRequest?.let { request ->
            holder.binding.request = request


            holder.binding.underMaintenanceButton.setOnClickListener {

            }


            holder.binding.doneButton.setOnClickListener {
                onMarkDone(request.id)
            }
        }
    }

    override fun getItemCount() = if (currentRequest != null) 1 else 0

    fun submitSingleRequest(request: Request?) {
        currentRequest = request
        notifyDataSetChanged()
    }
}
