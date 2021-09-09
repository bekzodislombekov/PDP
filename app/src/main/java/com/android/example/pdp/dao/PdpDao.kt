package com.android.example.pdp.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.android.example.pdp.models.Course
import com.android.example.pdp.models.Group
import com.android.example.pdp.models.Mentor

interface PdpDao {

    @Insert
    fun addCourse(course: Course)

    @Query("SELECT * FROM courses")
    fun getCourses(): List<Course>

    @Query("select * from courses where id=:id")
    fun getCourseById(id: Int): Course

    @Insert
    fun addMentor(mentor: Mentor)

    @Update
    fun updateMentor(mentor: Mentor)

    @Delete
    fun deleteMentor(mentor: Mentor)

    @Query("SELECT * FROM mentors")
    fun getMentors(): List<Mentor>

    @Query("SELECT * FROM groups")
    fun getGroups(): List<Group>
}