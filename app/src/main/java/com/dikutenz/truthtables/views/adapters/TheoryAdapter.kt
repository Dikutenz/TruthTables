package com.dikutenz.truthtables.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dikutenz.truthtables.R


class TheoryAdapter(private val itemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<TheoryAdapter.TheoryViewHolder>() {

    var items: ArrayList<String> = ArrayList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TheoryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_theory, parent, false
        )
        return TheoryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TheoryViewHolder, position: Int) {
        holder.bind(items[position], itemClickListener)
    }

    override fun getItemCount(): Int = items.size

    class TheoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text_view)

        fun bind(s: String, clickListener: OnItemClickListener) {
            itemTextView.text = s
            itemView.setOnClickListener { clickListener.onItemClicked(s) }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(s: String)
    }

}