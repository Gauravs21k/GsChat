package com.gaurav.gschat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView

class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val uid = intent.getStringExtra("uid")
        supportActionBar?.title = name
        chatRecyclerView = findViewById(R.id.chat_recycler_view)
        messageBox = findViewById(R.id.message_box)
        sendButton = findViewById(R.id.send_button)

    }
}