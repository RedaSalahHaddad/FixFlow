package com.example.fixflow.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.fixflow.databinding.ItemMessageBinding
import com.example.fixflow.models.Message
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter : ListAdapter<Message, ChatAdapter.MessageViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MessageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = getItem(position)
        holder.bind(message)
    }

    class MessageViewHolder(private val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(message: Message) {

            binding.senderNameTextView.text = message.senderName

            binding.messageTextView.text = message.content


            binding.timestampTextView.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp))
        }
    }


    class DiffCallback : DiffUtil.ItemCallback<Message>() {
        override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
            return oldItem == newItem
        }
    }
}
