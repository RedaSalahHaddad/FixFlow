package com.example.fixflow

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.example.fixflow.models.Message
import com.example.fixflow.adapters.ChatAdapter
import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class ChatActivity : AppCompatActivity() {

    private lateinit var db: FirebaseFirestore
    private lateinit var messagesRecyclerView: RecyclerView
    private lateinit var messageEditText: EditText
    private lateinit var sendButton: ImageButton
    private lateinit var chatAdapter: ChatAdapter
    private lateinit var chatId: String // معرف المحادثة
    private lateinit var currentUserId: String
    private lateinit var currentUserName: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)


        db = FirebaseFirestore.getInstance()


        messagesRecyclerView = findViewById(R.id.messagesRecyclerView)
        messageEditText = findViewById(R.id.messageEditText)
        sendButton = findViewById(R.id.sendButton)


        chatAdapter = ChatAdapter()
        messagesRecyclerView.layoutManager = LinearLayoutManager(this)
        messagesRecyclerView.adapter = chatAdapter


        chatId = "some_chat_id"


        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUserId = currentUser?.uid ?: "unknown"
        currentUserName = currentUser?.email ?: "Unknown User"


        listenForMessages()


        sendButton.setOnClickListener {
            val messageText = messageEditText.text.toString()
            if (messageText.isNotBlank()) {
                sendMessage(currentUserId, currentUserName, messageText, chatId)
                messageEditText.text.clear()
            } else {
                Toast.makeText(this, "Please enter a message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // إرسال الرسالة إلى Firestore
    private fun sendMessage(senderId: String, senderName: String, content: String, chatId: String) {
        val message = Message(
            id = UUID.randomUUID().toString(),
            senderId = senderId,
            senderName = senderName,
            content = content,
            timestamp = System.currentTimeMillis()
        )


        db.collection("chats").document(chatId).collection("messages")
            .document(message.id)
            .set(message)
            .addOnSuccessListener {
                Log.d("Chat", "Message sent successfully!")
            }
            .addOnFailureListener { e ->
                Log.e("Chat", "Error sending message", e)
            }
    }


    private fun listenForMessages() {
        val messagesRef = db.collection("chats").document(chatId).collection("messages")
        messagesRef.orderBy("timestamp", Query.Direction.ASCENDING)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    Log.w("Chat", "Listen failed.", e)
                    return@addSnapshotListener
                }

                val messages = mutableListOf<Message>()
                for (document in snapshot!!) {
                    val message = document.toObject(Message::class.java)
                    messages.add(message)
                }


                chatAdapter.submitList(messages)
            }
    }
}
