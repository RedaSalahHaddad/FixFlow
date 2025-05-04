package com.example.fixflow.receptionist.done

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixflow.R
import com.example.fixflow.databinding.ActivityDoneRequestsBinding
import com.example.fixflow.models.Request
import com.example.fixflow.receptionist.RequestsAdapter
import com.google.firebase.firestore.FirebaseFirestore

class DoneRequestsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoneRequestsBinding
    private lateinit var adapter: RequestsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoneRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RequestsAdapter(onDeleteClick = {}) // لا يوجد حذف هنا
        binding.doneRequestsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.doneRequestsRecyclerView.adapter = adapter

        FirebaseFirestore.getInstance().collection("requests")
            .whereEqualTo("status", "Done")
            .addSnapshotListener { snapshot, _ ->
                val list = snapshot?.mapNotNull { it.toObject(Request::class.java) } ?: emptyList()
                adapter.submitList(list)
            }
    }
}
