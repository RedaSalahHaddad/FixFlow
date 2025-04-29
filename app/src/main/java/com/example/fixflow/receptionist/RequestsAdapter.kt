package com.example.fixflow.receptionist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fixflow.databinding.ItemRequestBinding
import com.example.fixflow.models.Request

class RequestsAdapter(
    private val onDeleteClick: (String) -> Unit
) : RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {

    private var requests: List<Request> = listOf()

    inner class RequestViewHolder(val binding: ItemRequestBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = ItemRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        holder.binding.request = request
        holder.binding.deleteButton.setOnClickListener {
            onDeleteClick(request.id)
        }
    }

    override fun getItemCount() = requests.size

    fun submitList(list: List<Request>) {
        requests = list
        notifyDataSetChanged()
    }
}
