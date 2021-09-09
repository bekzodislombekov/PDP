package com.android.example.pdp

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.android.example.pdp.adapters.MentorAdapter
import com.android.example.pdp.database.AppDatabase
import com.android.example.pdp.databinding.DialogAddMentorBinding
import com.android.example.pdp.databinding.FragmentMentorBinding
import com.android.example.pdp.models.Course
import com.android.example.pdp.models.Group
import com.android.example.pdp.models.Mentor

class MentorFragment : Fragment() {
    private var param1: Int? = null
    private lateinit var binding: FragmentMentorBinding
    private lateinit var appDatabase: AppDatabase
    private lateinit var course: Course
    private lateinit var listMentors: ArrayList<Mentor>
    private lateinit var allMentors: ArrayList<Mentor>
    private lateinit var allGroups: ArrayList<Group>
    private lateinit var adapter: MentorAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getInt("id")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMentorBinding.inflate(layoutInflater, container, false)
        setHasOptionsMenu(true)
        (activity as AppCompatActivity).supportActionBar?.title = "Mentorlar ro'yxati"

        appDatabase = AppDatabase.getInstance(requireContext())
        course = appDatabase.pdpDao().getCourseById(param1!!)
        allMentors = ArrayList(appDatabase.pdpDao().getMentors())
        allGroups = ArrayList(appDatabase.pdpDao().getGroups())
        listMentors = ArrayList()
        for (mentor in allMentors) {
            if (mentor.course.id == course.id) {
                listMentors.add(mentor)
            }
        }

        adapter = MentorAdapter(listMentors, object : MentorAdapter.OnItemClickListener {
            override fun onEditListener(mentor: Mentor, position: Int) {
                val alertDialog = AlertDialog.Builder(requireContext())
                val dialogBinding = DialogAddMentorBinding.inflate(layoutInflater)
                alertDialog.setView(dialogBinding.root)
                dialogBinding.lastName.setText(mentor.lastName)
                dialogBinding.firstName.setText(mentor.firstName)
                alertDialog.setPositiveButton("O'zgartirish") { dialog, which ->
                    val lastName = dialogBinding.lastName.text.toString()
                    val firstName = dialogBinding.firstName.text.toString()
                    mentor.firstName = firstName
                    mentor.lastName = lastName
                    mentor.course = course
                    appDatabase.pdpDao().updateMentor(mentor)
                    listMentors[position].firstName = firstName
                    listMentors[position].lastName = lastName
                    adapter.notifyItemChanged(position)
                }
                alertDialog.show()
            }

            override fun onDeleteListener(mentor: Mentor, position: Int) {
                var isHave = false
                for (group in allGroups) {
                    if (group.mentor.id == mentor.id) {
                        isHave = true
                        break
                    }
                }

                if (isHave) {
                    Toast.makeText(
                        requireContext(),
                        "Siz bu mentorni o'chirolmaysiz, chunki u guruhga biriktirilgan",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    val alertDialog = AlertDialog.Builder(requireContext())
                    alertDialog.setMessage("Ushbu mentorni o'chirmoqchimisz")
                    alertDialog.setPositiveButton("Ha") { dialog, which ->
                        appDatabase.pdpDao().deleteMentor(mentor)
                        listMentors.remove(mentor)
                        adapter.notifyItemRemoved(position)
                        adapter.notifyItemRangeChanged(position, listMentors.size)
                    }
                    alertDialog.setNegativeButton("Yo'q") { dialog, which ->

                    }
                    alertDialog.show()
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
                val dialogBinding = DialogAddMentorBinding.inflate(layoutInflater)
                alertDialog.setView(dialogBinding.root)
                alertDialog.setPositiveButton("Saqlash") { dialog, which ->
                    val lastName = dialogBinding.lastName.text.toString()
                    val firstName = dialogBinding.firstName.text.toString()
                    val mentor = Mentor(lastName = lastName, firstName = firstName, course = course)
                    appDatabase.pdpDao().addMentor(mentor)
                    listMentors.add(mentor)
                }
                alertDialog.show()
            }
        }
        return true
    }

}