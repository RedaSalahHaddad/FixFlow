package com.example.fixflow.receptionist.done

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fixflow.databinding.ActivityDoneRequestsBinding
import com.example.fixflow.models.Request
import com.example.fixflow.receptionist.RequestsAdapter
import com.google.firebase.firestore.FirebaseFirestore

class DoneRequestsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoneRequestsBinding
    private lateinit var adapter: RequestsAdapter
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDoneRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = RequestsAdapter(onDeleteClick = { requestId ->
            db.collection("requests").document(requestId)
                .delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Request deleted", Toast.LENGTH_SHORT).show()
                }
        })

        binding.doneRequestsRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.doneRequestsRecyclerView.adapter = adapter

        db.collection("requests")
            .whereEqualTo("status", "Done")
            .addSnapshotListener { snapshot, _ ->
                val list = snapshot?.mapNotNull { it.toObject(Request::class.java) } ?: emptyList()
                adapter.submitList(list)
            }
    }
}
