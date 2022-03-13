package com.gaurav.gschat

import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class ChatActivity : AppCompatActivity() {
    private lateinit var chatRecyclerView: RecyclerView
    private lateinit var messageBox: EditText
    private lateinit var sendButton: ImageView
    private lateinit var messageAdapter: MessageAdapter
    private lateinit var messageList: ArrayList<Message>
    private lateinit var databaseReference: DatabaseReference

    var receiverRoom: String? = null
    var senderRoom: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        val name = intent.getStringExtra("name")
        val receiverUid = intent.getStringExtra("uid")

        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        supportActionBar?.title = name
        chatRecyclerView = findViewById(R.id.chat_recycler_view)
        messageBox = findViewById(R.id.message_box)
        sendButton = findViewById(R.id.send_button)
        messageList = ArrayList()
        messageAdapter = MessageAdapter(this, messageList)

        databaseReference = FirebaseDatabase.getInstance().reference

        sendButton.setOnClickListener {
            val message = messageBox.text.toString()
            val recentMessage = Message(message, senderUid)

            databaseReference.child("chats").child(senderRoom!!)
                .child("messages")
                .push()
                .setValue(recentMessage)
                .addOnSuccessListener{
                    databaseReference.child("chats").child(receiverRoom!!)
                        .child("messages")
                        .push()
                        .setValue(recentMessage)
                }
            messageBox.setText("")
        }
    }
}