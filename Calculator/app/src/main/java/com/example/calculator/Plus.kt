package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_plus.*
import kotlin.math.pow

class Plus : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus)

        val num1 = intent.getStringExtra("num1").toInt()
        val num2 = intent.getStringExtra("num2").toInt()
        val result = num1 + num2

        plusTextView.text = "$num1 + $num2 = $result"
    }
}
