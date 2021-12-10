package com.dikutenz.truthtables.views.adapters

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dikutenz.truthtables.R


class TableAdapter(
    private val values: ArrayList<Pair<String, ArrayList<Int>>>,
    private val context: Context
) :
    RecyclerView.Adapter<TableAdapter.TableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TableViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.table_row, parent, false)
        return TableViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TableViewHolder, position: Int) {
        holder.bind(values[position], context)
    }

    override fun getItemCount(): Int = values.size

    class TableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(value: Pair<String, ArrayList<Int>>, context: Context) {
            val row: LinearLayout = itemView.findViewById(R.id.row_view)
            var cell = (context as Activity).layoutInflater.inflate(R.layout.table_cell, null)
            cell.background = context.getDrawable(R.drawable.head_back)
            val titleTextView: TextView = cell.findViewById(R.id.title_text_view)
            titleTextView.text = value.first
            row.addView(cell)
            val list01: ArrayList<Int> = value.second
            for (i in list01.indices) {
                cell = context.layoutInflater.inflate(R.layout.table_cell, null)
                cell.background = if (i % 2 == 0) context.getDrawable(R.drawable.cell_back1)
                else context.getDrawable(R.drawable.cell_back2)
                val valueTextView: TextView = cell.findViewById(R.id.title_text_view)
                valueTextView.text = list01[i].toString()
                row.addView(cell)
            }
        }

    }
}