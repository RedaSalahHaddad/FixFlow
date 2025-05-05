package com.example.fixflow.models

data class Request(
    val id: String = "",
    val clientName: String = "",
    val deviceType: String = "",
    val problemDescription: String = "",
    val problemType: String = "",
    val status: String = "Pending",
    val createdAt: Long = System.currentTimeMillis()
)
