package com.example.calculator2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.View
import android.widget.Switch
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        btn_plus.setOnClickListener{
            if(number1.text.toString()=="" || number2.text.toString()=="")  toast("숫자를 입력하세요.")
            else{
                saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

                val num1 = number1.text.toString().toDouble()
                val num2 = number2.text.toString().toDouble()

                if(num1 == null || num2==null) toast("숫자를 입력하세요.")


                val result = num1 + num2
                resultTextView.text = "계산 결과 : $result"
            }
        }

        btn_minus.setOnClickListener{
            if(number1.text.toString()=="" || number2.text.toString()=="")  toast("숫자를 입력하세요.")
            else{
                saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

                val num1 = number1.text.toString().toDouble()
                val num2 = number2.text.toString().toDouble()

                if(num1 == null || num2==null) toast("숫자를 입력하세요.")


                val result = num1 - num2
                resultTextView.text = "계산 결과 : $result"
            }
        }

        btn_multi.setOnClickListener{
            if(number1.text.toString()=="" || number2.text.toString()=="")  toast("숫자를 입력하세요.")
            else{
                saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

                val num1 = number1.text.toString().toDouble()
                val num2 = number2.text.toString().toDouble()

                if(num1 == null || num2==null) toast("숫자를 입력하세요.")


                val result = num1 * num2
                resultTextView.text = "계산 결과 : $result"
            }
        }

        btn_devide.setOnClickListener{
            if(number1.text.toString()=="" || number2.text.toString()=="")  toast("숫자를 입력하세요.")
            else{
                saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

                val num1 = number1.text.toString().toDouble()
                val num2 = number2.text.toString().toDouble()

                if(num1 == null || num2==null) toast("숫자를 입력하세요.")


                val result = num1 / num2
                resultTextView.text = "계산 결과 : $result"
            }
        }

        remove_num1.setOnClickListener{
            number1.text = null
        }

        remove_num2.setOnClickListener{
            number2.text = null
        }

        remove_all.setOnClickListener{
            number1.text = null
            number2.text = null
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
