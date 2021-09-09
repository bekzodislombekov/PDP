package com.android.example.pdp.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import java.io.Serializable

@Entity(tableName = "courses")
data class Course(
    val id: Int = 0,
    val name: String,
    val about: String
) : Serializable

@Entity(tableName = "mentors")
data class Mentor(
    val id: Int = 0,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String,
    var course: Course
)

@Entity(tableName = "groups")
data class Group(
    val id: Int = 0,
    var name: String,
    var course: Course,
    var mentor: Mentor,
    var day: String,
    var time: String,
    var isOpened: Int
) : Serializable

@Entity(tableName = "students")
data class Student(
    val id: Int = 0,
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    var lastName: String,
    @ColumnInfo(name = "birth_date")
    var birthDate: String,
    var group: Group
) : Serializable