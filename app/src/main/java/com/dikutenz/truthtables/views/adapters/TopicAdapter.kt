package com.dikutenz.truthtables.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dikutenz.truthtables.R
import com.dikutenz.truthtables.model.enums.Topic
import com.dikutenz.truthtables.model.enums.Topic.*

class TopicAdapter(
    private val itemClickListener: OnItemClickListener,
    private val topics: List<Topic>,
) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.item_theory, parent, false
        )
        return TopicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(topics[position], itemClickListener)
    }

    override fun getItemCount(): Int = topics.size

    class TopicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemTextView: TextView = itemView.findViewById(R.id.item_text_view)

        fun bind(topic: Topic, itemClickListener: OnItemClickListener) {
            itemTextView.text = when(topic) {
                CREATE_TRUTH_TABLE -> "Построить таблицу истинности"
                FIND_SDNF -> "Найти СДНФ"
                FIND_SKNF -> "Найти СКНФ"
                EQUIVALENCE_FUNCTION -> "Проверить эквивалентность двух функций"
            }
            itemView.setOnClickListener { itemClickListener.onItemClicked(topic) }
        }
    }

    interface OnItemClickListener {
        fun onItemClicked(topic: Topic)
    }
}