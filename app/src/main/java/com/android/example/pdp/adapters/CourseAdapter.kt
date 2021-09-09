package com.android.example.pdp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.example.pdp.databinding.ItemCourseBinding
import com.android.example.pdp.models.Course

class CourseAdapter(
    private val list: List<Course>,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CourseAdapter.Vh>() {

    inner class Vh(private val itemCourseBinding: ItemCourseBinding) :
        RecyclerView.ViewHolder(itemCourseBinding.root) {

        fun bind(course: Course, position: Int) {
            itemCourseBinding.courseName.text = course.name
            itemCourseBinding.cardCourse.setOnClickListener {
                onItemClickListener.onItemClick(course)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(ItemCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.bind(list[position], position)
    }

    interface OnItemClickListener {
        fun onItemClick(course: Course)
    }
}