package com.black.edittextastextview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.black.edittextastextview.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val response = arrayOf("yes", "no", "prev", "skip")
    private val questionList = arrayListOf<Question>()
    private var curr: Int = 0
    private var focused = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val rvAdapter = RVAssessAdapter(this, response)

        binding.assessView.post {
            binding.assessView.adapter = rvAdapter
            binding.assessView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        }

        rvAdapter.setOnItemClickListener(object : OnRVItemClickListener {
            override fun onItemClick(pos: Int) {
                questionLogic(response[pos])
            }
        })

        getQuestion()

        binding.questionField.setEndIconOnClickListener {
            focused = !focused
            // change end icon
            binding.questionField.endIconDrawable = ContextCompat.getDrawable(
                this,
                if(focused) R.drawable.ic_edit_done else R.drawable.ic_edit_editing
            )

            // get the text from the input
            val inputText = binding.questionField.editText?.text.toString()
            binding.questionField.editText?.isFocusable = focused
            binding.questionField.editText?.isFocusableInTouchMode = false
            questionList[curr].question = inputText
            Log.d("CogTAG","${focused} $inputText ${curr}")
        }
    }

    private fun questionLogic(msg: String) {
        questionList[curr].answer = msg
        if (msg == "prev" && curr > 0) {
            curr--
        } else if (curr < questionList.size - 1) {
            curr++
        } else {
            curr = 0
        }
        Log.d("DummyQ", curr.toString())
        if (curr >= 0 && curr < questionList.size) {
            setQuestionInUI(questionList[curr].question)
            Handler(Looper.getMainLooper()).postDelayed({
              // fusionViewModel.setMessage(questionList[curr].question)
            }, 2000)
        }
    }

    private fun getQuestion() {
        questionList.add(Question("My Question 0","no"))
        questionList.add(Question("My Question 1","yes"))
        questionList.add(Question("My Question 2","no"))
        questionList.add(Question("My Question 3","yes"))
        questionList.add(Question("My Question 4","no"))
        questionList.add(Question("My Question 5","yes"))
        questionList.add(Question("My Question 6","no"))
        questionList.add(Question("My Question 7","yes"))
    }

    private fun setQuestionInUI(msg:String){
        binding.question.setText(msg)
    }
}