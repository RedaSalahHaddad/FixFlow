package com.example.fixflow.receptionist


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.fixflow.databinding.ItemRepairBinding

class RepairsAdapter(private val repairs: List<Repair>) : RecyclerView.Adapter<RepairsAdapter.RepairViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepairViewHolder {
        val binding = ItemRepairBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepairViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepairViewHolder, position: Int) {
        holder.bind(repairs[position])
    }

    override fun getItemCount(): Int = repairs.size

    inner class RepairViewHolder(private val binding: ItemRepairBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(repair: Repair) {
            binding.repair = repair
            binding.executePendingBindings()
        }
    }
}
