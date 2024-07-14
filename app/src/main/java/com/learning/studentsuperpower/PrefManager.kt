package com.learning.studentsuperpower

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import javax.inject.Inject

class PrefManager @Inject constructor(context: Context) {

    companion object {
        const val FILE_NAME = "STUDENT_POWER"
        const val NAME = "name"
        const val STANDARD_ID = "standard_id"
        const val STUDENT_ID = "student_id"
        const val STANDARD_NAME = "standard_name"
        const val SUBJECT = "subject"
        const val NULL = ""
    }

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(FILE_NAME, MODE_PRIVATE)

    private lateinit var editor: SharedPreferences.Editor

    var name: String?
        get() = sharedPreferences.getString(NAME, NULL)
        set(name) {
            editor = sharedPreferences.edit()
            editor.putString(NAME, name)
            editor.apply()
        }

    var standardId: Int?
        get() = sharedPreferences.getInt(STANDARD_ID, 0)
        set(standardId) {
            editor = sharedPreferences.edit()
            editor.putInt(STANDARD_ID, standardId!!)
            editor.apply()
        }

    var studentId: String?
        get() = sharedPreferences.getString(STUDENT_ID, NULL)
        set(studentId) {
            editor = sharedPreferences.edit()
            editor.putString(STUDENT_ID, studentId!!)
            editor.apply()
        }

    var standardName: String?
        get() = sharedPreferences.getString(STANDARD_NAME, NULL)
        set(standardName) {
            editor = sharedPreferences.edit()
            editor.putString(STANDARD_NAME, "12")
            editor.apply()
        }

    var subjectName: String?
        get() = sharedPreferences.getString(SUBJECT, NULL)
        set(subjectName) {
            editor = sharedPreferences.edit()
            editor.putString(SUBJECT, subjectName)
            editor.apply()
        }
}