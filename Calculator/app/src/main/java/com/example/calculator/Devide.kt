package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_devide.*
import kotlinx.android.synthetic.main.activity_plus.*

class Devide : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_devide)

        val num1 = intent.getStringExtra("num1").toInt()
        val num2 = intent.getStringExtra("num2").toInt()
        val result1 = num1 / num2
        val result2 = num1 % num2


        devideTextView.text = "몫은 ${result1}, 나머지는 $result2"
    }
}
