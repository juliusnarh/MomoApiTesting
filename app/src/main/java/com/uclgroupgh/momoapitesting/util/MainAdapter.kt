package com.uclgroupgh.momoapitesting.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.uclgroupgh.momoapitesting.R

class MainAdapter : RecyclerView.Adapter<MainAdapter.ViewHolder>() {
    var list: List<Int> = arrayListOf()
    var tracker: SelectionTracker<Long>? = null
    var limit: Int? = null

    init {
        setHasStableIds(true)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val number = list[position]
        tracker?.let {
            if (limit != null && it.selection.size() >= limit!!) {
                if (it.isSelected(position.toLong())) {
                    holder.bind(number, it.isSelected(position.toLong()))
                }
            } else
                holder.bind(number, it.isSelected(position.toLong()))

        }
    }

    override fun getItemId(position: Int): Long = position.toLong()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_numbers, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private var numberBall: TextView = view.findViewById(R.id.number)

        fun bind(value: Int, isActivated: Boolean = false) {
            numberBall.text = value.toString()

            itemView.isActivated = isActivated
        }

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition

                override fun getSelectionKey(): Long = itemId
            }
    }
}