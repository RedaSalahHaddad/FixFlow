package com.example.fixflow.receptionist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fixflow.databinding.ItemRequestBinding

class RequestsAdapter : RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {

    private var requests = listOf<String>()

    fun submitList(list: List<String>) {
        requests = list
        notifyDataSetChanged()
    }

    class RequestViewHolder(private val binding: ItemRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(request: String) {
            binding.requestText.text = request
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = ItemRequestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        holder.bind(requests[position])
    }

    override fun getItemCount() = requests.size
}
