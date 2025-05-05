package com.example.fixflow.models


data class Message(
    val id: String = "",             // ID فريد للرسالة (Firestore Document ID)
    val senderId: String = "",       // ID المرسل (User ID من FirebaseAuth)
    val senderName: String = "",
    val content: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

