package com.gaurav.gschat

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messageList : ArrayList<Message>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0){
            ReceivedMessageHolder(LayoutInflater.from(context)
                .inflate(R.layout.received_message, parent, false))
        } else {
            SentMessageHolder(LayoutInflater.from(context)
                .inflate(R.layout.sent_message, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messageList[position]
        if (holder.itemViewType == 0) {
            val viewHolder = holder as ReceivedMessageHolder
            holder.receivedMessage.text = currentMessage.message
        } else {
            val viewHolder = holder as SentMessageHolder
            holder.sentMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messageList[position]
        return if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            0
        } else {
            1
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    class SentMessageHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val sentMessage = itemView.findViewById<TextView>(R.id.text_sent_message)
    }

    class ReceivedMessageHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val receivedMessage = itemView.findViewById<TextView>(R.id.text_received_message)
    }

}