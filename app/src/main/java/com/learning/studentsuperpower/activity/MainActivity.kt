package com.learning.studentsuperpower.activity

import android.annotation.SuppressLint
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.JsonObject
import com.learning.studentsuperpower.PrefManager
import com.learning.studentsuperpower.PrefManager.Companion.NULL
import com.learning.studentsuperpower.R
import com.learning.studentsuperpower.StandardInterface
import com.learning.studentsuperpower.Utils
import com.learning.studentsuperpower.Utils.addBackground
import com.learning.studentsuperpower.adapters.ChipAdapter
import com.learning.studentsuperpower.api.APIClient
import com.learning.studentsuperpower.api.ApiInterface
import com.learning.studentsuperpower.databinding.ActivityMainBinding
import com.learning.studentsuperpower.fragments.HomeStdFragment
import com.learning.studentsuperpower.models.Question
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var m: ActivityMainBinding
    private lateinit var pref: PrefManager
    private lateinit var api: ApiInterface
    private var layoutVisibility: Int = 0
    private var chipsSubjects = ArrayList<String>()
    private var chipOptions = ArrayList<String>()
    private lateinit var lm: GridLayoutManager
    private lateinit var lmS: GridLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        m = ActivityMainBinding.inflate(LayoutInflater.from(this), null, false)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(m.root)
        init()
    }

    private fun init() {
        if (!isDestroyed && !isFinishing) {
            api = APIClient.getClient(0).create(ApiInterface::class.java)
            pref = PrefManager(this@MainActivity)
            lm = GridLayoutManager(this, 2)
            lmS = GridLayoutManager(this, 2)

            clickListeners()
        }
    }

    private fun clickListeners() {
        layoutOne()
        m.btnStart.btn.setOnClickListener {
            m.btnStart.btn.text = ""
            m.btnStart.loading.visibility = VISIBLE

            if (layoutVisibility == 1) {
                layoutTwo()
                m.btnStart.btn.text = getString(R.string.start)
                m.btnStart.loading.visibility = GONE
            } else if (layoutVisibility == 2) {
                pref.name = m.addName.txtEt.text!!.trim().toString()
                pref.standardId = 1
                pref.standardName = m.addStandard.txtEt.text!!.trim().toString()
                pref.subjectName = "Science"
                if (isValid) {
                    sendUserData()
                }
                m.btnStart.btn.text = getString(R.string.start)
                m.btnStart.loading.visibility = GONE
            }
        }
        m.addStandard.txtEt.setTextColor(ContextCompat.getColor(this, R.color.black))
        addDataToChipList()

        click(m.addStandard.txtEt)
        click(m.addStandard.root)
        click(m.addStandard.item)
        click(m.addStandard.imgPrevious)
    }

    @SuppressLint("SetTextI18n")
    private fun click(view: View) {
        if (!isDestroyed && !isFinishing) {
            view.setOnClickListener {
                val s = HomeStdFragment(object : StandardInterface {
                    override fun onSuccess(standardName: String) {
                        m.addStandard.txtEt.setText("Standard $standardName")
                        Toast.makeText(
                            this@MainActivity,
                            "Standard $standardName",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                    }

                    override fun onFailure() {
                    }

                })
                Log.d("TAG", "clickListeners:1 ")
                if (!isDestroyed && !isFinishing && !supportFragmentManager.isDestroyed) {
                    Log.d("TAG", "clickListeners:2 ")
                    s.show(supportFragmentManager, s.javaClass.name)
                }
            }

        }
    }

    private fun layoutOne() {
        if (!isFinishing && !isDestroyed) {
            layoutVisibility = 1
            m.linearOne.visibility = VISIBLE
            m.linearTwo.visibility = GONE
            m.btmNavView.visibility = GONE
            m.layoutThree.visibility = GONE
        }
    }

    private fun layoutTwo() {
        if (!isFinishing && !isDestroyed) {
            layoutVisibility = 2
            m.addStandard.txtEt.isEnabled = false
            m.linearTwo.visibility = VISIBLE
            m.linearOne.visibility = GONE
            m.btmNavView.visibility = GONE
            m.layoutThree.visibility = GONE
            addBackground(m.addStandard.item)
            m.addName.txtEt.hint = getString(R.string.enter_your_name)
            m.addStandard.txtEt.hint = getString(R.string.standard)
            m.addStandard.imgPrevious.visibility = VISIBLE
            addBackground(m.addName.item)
            m.addStandard.imgPrevious.addBackground(
                startColorResId = R.color.black,
                cornerRadiusResId = R.dimen.round_corner
            )
        }
    }

    private fun layoutThree() {
        if (!isDestroyed && !isFinishing) {
            layoutVisibility = 2
            m.linearTwo.visibility = GONE
            m.linearOne.visibility = GONE
            m.layoutThree.visibility = VISIBLE
            m.question.text = "Do you like study in general ?"

            addOptionsToDataList()
        }
    }

    private fun addBackground(view: View) {
        view.addBackground(
            startColorResId = R.color.chip1,
            endColorResId = R.color.chip2,
            angle = GradientDrawable.Orientation.LEFT_RIGHT,
            cornerRadiusResId = R.dimen.chip_corner
        )

    }

    private val isValid: Boolean
        get() {
            when {
                pref.name.isNullOrEmpty() -> {
                    Utils.getToolTip(this@MainActivity, "Please Enter Name.!!", 0.5f)
                        .showAlignBottom(m.addName.txtEt)
                    return false
                }

                pref.standardId == 0 || pref.standardName.isNullOrEmpty() -> {
                    Utils.getToolTip(this@MainActivity, "Please Select Standard.!!", 0.5f)
                        .showAlignBottom(m.addStandard.txtEt)
                    return false
                }

                pref.subjectName == NULL -> {
                    Utils.getToolTip(this@MainActivity, "Select Your Subjects.!!", 0.5f)
                        .showAlignBottom(m.addName.txtEt)
                    return false
                }
            }
            return true
        }

    private fun addDataToChipList() {
        if (!isFinishing && !isDestroyed) {
            m.rvChips.layoutManager = lm
            chipsSubjects.add(0, "Science")
            chipsSubjects.add(1, "Maths")
            chipsSubjects.add(2, "Social Science")
            chipsSubjects.add(3, "English")
            chipsSubjects.add(4, "Gujarati")

            val adapter = ChipAdapter(chipsSubjects)
            m.rvChips.adapter = adapter
            adapter.notifyItemRangeInserted(0, chipsSubjects.size)
        }
    }

    private fun addOptionsToDataList() {
        if (!isDestroyed && !isFinishing) {
            m.rvQChips.layoutManager = lmS
            chipsSubjects.add(0, "Yes")
            chipsSubjects.add(1, "No")
            chipsSubjects.add(2, "Not Sure")
            chipsSubjects.add(3, "Like some subjects but not study in general")
            chipsSubjects.add(4, "Other")

            val adapter = ChipAdapter(chipOptions)
            m.rvQChips.adapter = adapter
            adapter.notifyItemRangeInserted(0, chipOptions.size)
        }
    }

    private fun sendUserData() {
        if (!isFinishing && !isDestroyed) {
            val data = JsonObject()
            data.addProperty("name", m.addName.txtEt.text.toString().trim())
            data.addProperty("standard", m.addStandard.txtEt.text.toString().trim())
            data.addProperty("subject", m.addStandard.txtEt.text.toString().trim())
            val call = api.sendUserData(data)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.body() != null && response.isSuccessful) {
                        val resBody = response.body()!!.string()
                        val jsonVersion = JSONObject(resBody)
                        val studentId =
                            if (jsonVersion.has("student_id")) jsonVersion.getString("student_id") else ""

                        pref.studentId = studentId
                        layoutThree()
                         // questionsToUser("i want to know about AI.")
                        Log.d("TAG", "studentId:$studentId ")
                        //  m.txtVersion.text = version
                    } else {
                        Toast.makeText(this@MainActivity, "resBody is Empty", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                    Toast.makeText(this@MainActivity, "Failed to send data", Toast.LENGTH_SHORT)
                        .show()
                }

            })
        }
    }


    fun parseJsonResponse(jsonResponse: String): List<Question> {
        val questionsList = mutableListOf<Question>()

        try {
            // Parse the JSON response
            val jsonObject = JSONObject(jsonResponse)
            val responseString = jsonObject.getString("response")
            val responseJson = JSONObject(responseString)

            // Debug: Print the parsed response JSON
            println("Parsed Response JSON: $responseJson")

            // Extract the questions array
            val questionsJsonArray = responseJson.getJSONArray("questions")

            // Loop through each question object
            for (i in 0 until questionsJsonArray.length()) {
                val questionObject = questionsJsonArray.getJSONObject(i)
                val questionText = questionObject.getString("questions")

                // Extract the options array
                val optionsJsonArray = questionObject.getJSONArray("options")
                val options = mutableListOf<String>()
                for (j in 0 until optionsJsonArray.length()) {
                    val optionObject = optionsJsonArray.getJSONObject(j)
                    val option = optionObject.keys().next()
                    options.add(optionObject.getString(option))
                }

                // Create a Question object and add it to the list
                questionsList.add(Question(questionText, options))
            }
        } catch (e: Exception) {
            e.printStackTrace()
            println("Error parsing JSON response: ${e.message}")
        }

        return questionsList
    }

    private fun questionsToUser(query: String) {
        if (!isFinishing && !isDestroyed) {
            val data = JsonObject().apply {
                addProperty("query", query)
                addProperty("student_id", pref.studentId)
                addProperty("tone", "Dramatic")
            }

            val call = api.questionToUser(data)
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful && response.body() != null) {
                        val resBody = response.body()!!.string()
                        m.question.text = "Do you like study in general ?"
                        val questions = parseJsonResponse(resBody)

                        // Print the extracted questions and options
                        questions.forEach { question ->
                            println("Question: ${question.questionText}")
                            println("Options:")
                            question.options.forEach { println(it) }
                        }
                        layoutThree()
                    } else {
                        Toast.makeText(this@MainActivity, "resBody is Empty", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Failed to send data", Toast.LENGTH_SHORT)
                        .show()
                }
            })
        }
    }


    private fun fetchAppVersion() {
        if (!isDestroyed && !isFinishing) {
            val api = APIClient.getClient(1).create(ApiInterface::class.java)
            val call = api.getAppVersion
            call.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(p0: Call<ResponseBody>, p1: Response<ResponseBody>) {
                    if (p1.body() != null) {
                        val resBody = p1.body()!!.string()
                        val jsonVersion = JSONObject(resBody)
                        val version =
                            if (jsonVersion.has("version")) jsonVersion.getString("version") else "No Version Found..."
                        Log.d("TAG", "App Version: version")
                        //  m.txtVersion.text = version
                    } else {
                        Toast.makeText(this@MainActivity, "resBody is Empty", Toast.LENGTH_SHORT)
                            .show()
                    }

                }

                override fun onFailure(p0: Call<ResponseBody>, p1: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }
    }
}