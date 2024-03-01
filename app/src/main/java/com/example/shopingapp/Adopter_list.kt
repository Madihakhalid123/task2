package com.example.shopingapp

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.core.View

class Adopter_list(
    private val context: Context,
    private val dataList: List<ModelTask>,
    var listener: OnItemClickListener
) :
    RecyclerView.Adapter<Adopter_list.ViewHolder>() {
    interface OnItemClickListener {
        fun ondeleteClick(modelChat: ModelTask)
        fun oneditclick(modelChat: ModelTask)
        fun onItemClick(modelChat: ModelTask)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.task_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(dataList[position])
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: android.view.View) : RecyclerView.ViewHolder(itemView) {


        var title = itemView.findViewById<TextView>(R.id.title)
        var description = itemView.findViewById<TextView>(R.id.des)
        var delete = itemView.findViewById<ImageButton>(R.id.delete)
        var container = itemView.findViewById<LinearLayout>(R.id.container)
        var edit=itemView.findViewById<ImageButton>(R.id.edit)


        fun bind(modelChat: ModelTask) {
            title.text = modelChat.title
            description.text = modelChat.description
            delete.setOnClickListener() {
                listener.ondeleteClick(modelChat)
            }
            edit.setOnClickListener(){
                listener.oneditclick(modelChat)
            }
            container . setOnClickListener (){
                listener.onItemClick(modelChat)
            }


        }
    }
}

