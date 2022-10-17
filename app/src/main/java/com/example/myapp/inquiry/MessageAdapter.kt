package com.example.myapp.inquiry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(val context: Context, val messegeList: ArrayList<Message>):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val ITEM_RECEIVE = 1
    val ITEM_SEND = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == 1) {
            val view: View = LayoutInflater.from(context).inflate(R.layout.recieve, parent, false)
            return ReceiveVidwHolder(view)
        } else {
            val view: View = LayoutInflater.from(context).inflate(R.layout.send, parent, false)
            return SendVidwHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val currentMessage = messegeList[position]

        if (holder.javaClass == SendVidwHolder::class.java) {
            // SendViewHolder
            val viewHolder = holder as SendVidwHolder

            holder.sendMessage.text = currentMessage.message
        } else {
            val viewHolder = holder as ReceiveVidwHolder

            holder.receiveMessage.text = currentMessage.message
        }
    }

    override fun getItemViewType(position: Int): Int {
        val currentMessage = messegeList[position]

        if (FirebaseAuth.getInstance().currentUser?.uid.equals(currentMessage.senderId)) {
            return ITEM_RECEIVE
        } else {
            return ITEM_SEND
        }
    }

    override fun getItemCount(): Int {
        return messegeList.size
    }

    class SendVidwHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val sendMessage = itemView.findViewById<TextView>(R.id.send_message_area)
    }

    class ReceiveVidwHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val receiveMessage = itemView.findViewById<TextView>(R.id.receive_message_area)
    }
}