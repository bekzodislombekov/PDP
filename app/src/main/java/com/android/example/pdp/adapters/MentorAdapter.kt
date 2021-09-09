package com.android.example.pdp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.example.pdp.databinding.ItemMentorBinding
import com.android.example.pdp.models.Mentor

class MentorAdapter(private val list: List<Mentor>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MentorAdapter.Vh>() {

    inner class Vh(private val itemMentorBinding: ItemMentorBinding) :
        RecyclerView.ViewHolder(itemMentorBinding.root) {

        fun bind(mentor: Mentor, position: Int) {
            itemMentorBinding.lastName.text = "${mentor.firstName} ${mentor.lastName}"
            itemMentorBinding.cardDelete.setOnClickListener {
                onItemClickListener.onDeleteListener(mentor, position)
            }
            itemMentorBinding.cardUpdate.setOnClickListener {
                onItemClickListener.onEditListener(mentor, position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh = Vh(
        ItemMentorBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(list[position], position)
    }

    interface OnItemClickListener {
        fun onEditListener(mentor: Mentor, position: Int)

        fun onDeleteListener(mentor: Mentor, position: Int)
    }
}