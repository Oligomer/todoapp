package com.dpug.todoapp.fragments.list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.dpug.todoapp.R
import com.dpug.todoapp.data.models.Priority.*
import com.dpug.todoapp.data.models.ToDoData

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<ToDoData>()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder =
            MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.row_layout, parent, false))
//            MyViewHolder(RowLayoutBinding.inflate(LayoutInflater.from(parent.context)).root)
    override fun getItemCount() = dataList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val title = holder.itemView.findViewById<TextView>(R.id.title_txt)
        val description = holder.itemView.findViewById<TextView>(R.id.description_txt)
        val indicator = holder.itemView.findViewById<CardView>(R.id.priority_indicator)
        val ctx = holder.itemView.context
        val rowBackground = holder.itemView.findViewById<ConstraintLayout>(R.id.row_background)
        val action = ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position], dataList[position])

        rowBackground.setOnClickListener() {
            holder.itemView.findNavController().navigate(action)
        }

        title.text = dataList[position].title
        description.text = dataList[position].description

        indicator.background.setTint(when (dataList[position].priority) {
                    HIGH -> color(ctx, R.color.red)
                    MEDIUM -> color(ctx, R.color.yellow)
                    LOW -> color(ctx, R.color.green)
                }
            )
    }

    fun setData(toDoData: List<ToDoData>){
        this.dataList = toDoData
        notifyDataSetChanged()
    }
}
private fun color(context: Context, @ColorRes id: Int) = ContextCompat.getColor(context, id)