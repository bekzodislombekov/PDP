package com.android.example.pdp

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.android.example.pdp.adapters.CourseAdapter
import com.android.example.pdp.database.AppDatabase
import com.android.example.pdp.databinding.DialogAddCourseBinding
import com.android.example.pdp.databinding.FragmentCourseBinding
import com.android.example.pdp.models.Course

class CourseFragment : Fragment() {
    private var param: Int? = null
    private lateinit var binding: FragmentCourseBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var listCourse: ArrayList<Course>
    private lateinit var adapter: CourseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getInt("id")
        }
        (activity as AppCompatActivity).supportActionBar?.title = "Barcha kurslar ro'yxati"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseBinding.inflate(layoutInflater, container, false)
        if (param == 1) {
            setHasOptionsMenu(true)
        } else {
            (activity as AppCompatActivity).supportActionBar?.title = "Barcha kurslar"
            setHasOptionsMenu(false)
        }
        appDatabase = AppDatabase.getInstance(requireContext())
        listCourse = ArrayList(appDatabase.pdpDao().getCourses())
        adapter = CourseAdapter(listCourse, object : CourseAdapter.OnItemClickListener {
            override fun onItemClick(course: Course) {
                when (param) {
                    1 -> {
                        val bundle = Bundle()
                        bundle.putInt("id", course.id)
                        findNavController().navigate(R.id.itemCourseFragment, bundle)
                    }
                    2 -> {
                        val bundle = Bundle()
                        bundle.putSerializable("id", course.id)
                        findNavController().navigate(R.id.groupFragment, bundle)
                    }
                    3 -> {
                        val bundle = Bundle()
                        bundle.putSerializable("course", course)
                        findNavController().navigate(R.id.mentorFragment, bundle)
                    }
                }
            }

        })
        binding.rv.adapter = adapter
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_add, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                val alertDialog = AlertDialog.Builder(requireContext())
                val dialogBinding = DialogAddCourseBinding.inflate(layoutInflater)
                alertDialog.setView(dialogBinding.root)
                alertDialog.setPositiveButton("Qo'shish") { dialog, which ->
                    val name = dialogBinding.courseName.text.toString()
                    val about = dialogBinding.courseAbout.text.toString()
                    val course = Course(name = name, about = about)
                    appDatabase.pdpDao().addCourse(course)
                    listCourse.add(course)
                    adapter.notifyItemInserted(listCourse.size)
                }
                alertDialog.setNegativeButton("Yopish") { _, _ ->

                }
                alertDialog.show()
            }
        }
        return true
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = "Barcha kurslar ro'yxati"
    }

}