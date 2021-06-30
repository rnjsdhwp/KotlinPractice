package com.example.calculator4

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import androidx.annotation.RequiresApi
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.lang.reflect.Array

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        val arr = arrayOf(btn0,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9, //0~9
            plusButton,minusButton,multiButton,devideButton,    //10~13
            removeNum1, removeNum2, removeAll)  //14~16
        var str1 = ""
        var str2 = ""

        for(i in 0..arr.size-1){
            when (i){
                in 0..9 -> arr[i].setOnClickListener {
                    if(number1.isFocused){
                        str1 = number1.text.toString()
                        str1 += i.toString()
                        number1.setText(str1)
                    }
                    else if(number2.isFocused){
                        str2 = number2.text.toString()
                        str2 += i.toString()
                        number2.setText(str2)
                    }else   toast("숫자를 입력할 곳을 누르세요")
                }
                in 10..13 -> arr[i].setOnClickListener {
                    if (number1.text.toString() == "" || number2.text.toString() == "")     toast("숫자를 눌러주세요")
                    else{
                        saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

                        val num1 = number1.text.toString().toDouble()
                        val num2 = number2.text.toString().toDouble()

                        if(num1 == null || num2==null) toast("숫자를 입력하세요.")

                        var result = 0.0
                        when (i) {
                            10 -> result = num1 + num2
                            11 -> result = num1 - num2
                            12 -> result = num1 * num2
                            13 -> result = num1 / num2
                        }
                        resultTextView.text = "계산 결과 : $result"
                    }
                }
                14 -> arr[i].setOnClickListener{
                    number1.text = null
                }
                15-> arr[i].setOnClickListener{
                    number2.text = null
                }
                16 -> arr[i].setOnClickListener{
                    number1.text = null
                    number2.text = null
                    resultTextView.text = "계산 결과 :"
                }
            }
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
