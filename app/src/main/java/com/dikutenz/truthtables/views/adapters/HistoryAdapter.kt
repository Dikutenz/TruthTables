package com.dikutenz.truthtables.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.entities.BooleanFunction

class HistoryAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    var items: List<BooleanFunction> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_theory, parent, false
        )
        return HistoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(items[position], itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    class HistoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text_view)

        fun bind(booleanFunction: BooleanFunction, clickListener: OnItemClickListener) {
            itemTextView.text = booleanFunction.value

            itemView.setOnClickListener { clickListener.onItemClicked(booleanFunction) }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(booleanFunction: BooleanFunction)
    }
}