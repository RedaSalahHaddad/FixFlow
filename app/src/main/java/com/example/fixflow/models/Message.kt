package com.example.fixflow.models


data class Message(
    val id: String = "",
    val senderId: String = "",
    val senderName: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

