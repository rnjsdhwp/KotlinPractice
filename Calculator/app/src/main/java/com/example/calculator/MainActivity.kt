package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        plusButton.setOnClickListener{
            saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

            startActivity<Plus>(
                "num1" to number1.text.toString(),
                "num2" to number2.text.toString()
            )
        }

        minusButton.setOnClickListener{
            saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

            startActivity<Minus>(
                "num1" to number1.text.toString(),
                "num2" to number2.text.toString()
            )
        }

        multiButton.setOnClickListener{
            saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

            startActivity<Multi>(
                "num1" to number1.text.toString(),
                "num2" to number2.text.toString()
            )
        }

        devideButton.setOnClickListener{
            saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

            startActivity<Devide>(
                "num1" to number1.text.toString(),
                "num2" to number2.text.toString()
            )
        }
    }

    private fun saveData(num1:Int, num2:Int){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val editor = pref.edit()

        editor.putInt("KEY_NUM1", num1)
            .putInt("KEY_NUM2", num2)
            .apply()
    }
    private fun loadData(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this)
        val num1 = pref.getInt("KEY_NUM1", 0)
        val num2 = pref.getInt("KEY_NUM2", 0)

        if(num1 != 0 && num2 != 0){
            number1.setText(num1.toString())
            number2.setText(num2.toString())
        }
    }
}
