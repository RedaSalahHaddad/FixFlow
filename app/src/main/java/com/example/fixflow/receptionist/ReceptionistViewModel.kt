package com.example.fixflow.receptionist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData

class ReceptionistViewModel : ViewModel() {
    val userName = MutableLiveData<String>("")

    val repairs = MutableLiveData<List<Repair>>(
        listOf(
            Repair("001", "iPhone 13", "In Progress", "Battery replacement"),
            Repair("002", "Samsung S21", "Pending", "Screen replacement")
        )
    )
}
