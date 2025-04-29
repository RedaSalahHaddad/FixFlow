package com.example.fixflow.models

data class Request(
    var id: String = "",
    var clientName: String = "",
    var deviceType: String = "",
    var problemDescription: String = "",
    var problemType: String = ""
)
