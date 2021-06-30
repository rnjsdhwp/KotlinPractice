package com.example.calculator3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        loadData()

        registerForContextMenu(resultTextView)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.plusButton -> {
                if(number1.text.toString()=="" || number2.text.toString()=="")  toast("숫자를 입력하세요.")
                else {
                    saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

                    val num1 = number1.text.toString().toInt()
                    val num2 = number2.text.toString().toInt()
                    val result = num1 + num2

                    resultTextView.text = "계산 결과 : $result"
                }
            }
            R.id.minusButton -> {
                if(number1.text.toString()=="" || number2.text.toString()=="")  toast("숫자를 입력하세요.")
                else {
                    saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

                    val num1 = number1.text.toString().toInt()
                    val num2 = number2.text.toString().toInt()
                    val result = num1 - num2

                    resultTextView.text = "계산 결과 : $result"
                }
            }
            R.id.multiButton -> {
                if(number1.text.toString()=="" || number2.text.toString()=="")  toast("숫자를 입력하세요.")
                else {
                    saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

                    val num1 = number1.text.toString().toInt()
                    val num2 = number2.text.toString().toInt()
                    val result = num1 * num2

                    resultTextView.text = "계산 결과 : $result"
                }
            }
            R.id.devideButton -> {
                if(number1.text.toString()=="" || number2.text.toString()=="")  toast("숫자를 입력하세요.")
                else {
                    saveData(number1.text.toString().toInt(), number2.text.toString().toInt())

                    val num1 = number1.text.toString().toDouble()
                    val num2 = number2.text.toString().toDouble()
                    val result = num1 / num2

                    resultTextView.text = "계산 결과 : $result"
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.removeNum1 -> {
                number1.text = null
            }
            R.id.removeNum2 -> {
                number2.text = null
            }
            R.id.removeAll -> {
                number1.text = null
                number2.text = null
                resultTextView.text = "계산 결과 :"
            }
        }
        return super.onContextItemSelected(item)
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
