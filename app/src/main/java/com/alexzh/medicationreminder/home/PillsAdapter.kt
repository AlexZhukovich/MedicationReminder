package com.alexzh.medicationreminder.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.data.model.Pill
import kotlinx.android.synthetic.main.item_pill.view.*

class PillsAdapter : RecyclerView.Adapter<PillsAdapter.ViewHolder>() {
    private val mPills = mutableListOf<Pill>()

    fun addPills(pills: List<Pill>) {
        mPills.addAll(pills)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PillsAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_pill, parent, false)
        return PillsAdapter.ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mPills[position])
    }

    override fun getItemCount(): Int  = mPills.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(pill: Pill) {
            with(pill) {
                itemView.pillTitle.text = name
            }
        }
    }
}