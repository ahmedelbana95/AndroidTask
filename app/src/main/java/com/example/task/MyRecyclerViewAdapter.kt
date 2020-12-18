package com.example.task

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyRecyclerViewAdapter internal constructor(context: Context?, data: MutableMap<String, Int>) :
    RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder>() {
    private val mData: MutableMap<String, Int> = data
    private val mInflater: LayoutInflater = LayoutInflater.from(context)

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = mInflater.inflate(R.layout.recycler_view_row, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvKey.text=mData.keys.toList()[position]
        holder.tvValue.text=mData[mData.keys.toList()[position]].toString()
        //        holder.myTextView.setText(animal);
    }

    // total number of rows
    override fun getItemCount(): Int {
        return mData.size
    }

    // stores and recycles views as they are scrolled off screen
    inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        var tvKey: TextView = itemView.findViewById(R.id.tvKey)
        var tvValue: TextView = itemView.findViewById(R.id.tvValue)
    }
}