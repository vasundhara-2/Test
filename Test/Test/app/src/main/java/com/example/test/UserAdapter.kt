package com.example.test

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.test.R

class UserAdapter(private val listener : (User) -> Unit) : ListAdapter<User, UserAdapter.ViewHolder>(DiffUserCallBack()){
    lateinit var context: Context

    inner class ViewHolder(private val containerView : View) : RecyclerView.ViewHolder(containerView){
        init {
            itemView.setOnClickListener {
                listener.invoke(getItem(adapterPosition))
            }
        }

        fun bind(user : User){
            containerView.findViewById<TextView>(R.id.name).text = user.name
            containerView.findViewById<TextView>(R.id.desc).text = user.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemLayout = LayoutInflater.from(parent.context).inflate(R.layout.user_layout, parent, false)
        return ViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class DiffUserCallBack : DiffUtil.ItemCallback<User>(){
    override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
        return oldItem == newItem
    }

}