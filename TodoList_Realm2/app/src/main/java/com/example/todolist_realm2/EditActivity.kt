package com.example.todolist_realm2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.util.*

class EditActivity : AppCompatActivity() {
    val realm = Realm.getDefaultInstance()
    val calendar: Calendar = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val id = intent.getLongExtra("id", -1L)
        if(id == -1L){
            insertMode()
        }else{
            updateMode(id)
        }

        calendarView.setOnDateChangeListener{view, year, month, datOfMonth ->
            calendar.set(Calendar.MONTH, month)
            calendar.set(Calendar.DAY_OF_MONTH, datOfMonth)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }

    private fun insertTodo(){
        realm.beginTransaction()    //트랜잭션 시작
        val todo = realm.createObject<Todo>(nextId())   //새 객체 생성
        todo.title = todoEditText1.text.toString()   //값 설정
        todo.number = todoEditText2.text.toString()
        todo.address = todoEditText3.text.toString()
        todo.date = calendar.timeInMillis
        realm.commitTransaction()   //트랜잭션 종료

        alert("내용이 추가되었습니다."){
            yesButton { finish() }
        }.show()    //다이얼로그 표시
    }

    private fun nextId(): Int{  //다음 id반환
        val maxId = realm.where<Todo>().max("id")
        if(maxId != null){
            return maxId.toInt()+1
        }
        return 0
    }

    private fun updateTodo(id: Long){
        realm.beginTransaction()
        //!!: todo는 이후부터 null이 아님
        val todo = realm.where<Todo>().equalTo("id", id).findFirst()!!
        todo.title = todoEditText1.text.toString()
        todo.number = todoEditText2.text.toString()
        todo.address = todoEditText3.text.toString()
        todo.date = calendar.timeInMillis
        realm.commitTransaction()

        alert("내용이 변경되었습니다.") {
            yesButton { finish() }
        }.show()
    }

    private fun deleteTodo(id: Long){
        realm.beginTransaction()
        val todo = realm.where<Todo>().equalTo("id", id).findFirst()!!
        todo.deleteFromRealm()  //deleteFromRealm 메서드로 삭제
        realm.commitTransaction()

        alert("내용이 삭제되었습니다.") {
            yesButton { finish() }
        }.show()
    }

    private fun insertMode(){
        deleteFab.visibility = View.GONE    //삭제 버튼을 감추기

        //오류 발생시 deleteFab.hide()
        doneFab.setOnClickListener {
            insertTodo()
        }
    }

    private fun updateMode(id: Long){
        //id에 해당하는 객체를 화면에 표시
        val todo = realm.where<Todo>().equalTo("id", id).findFirst()!!
        todoEditText1.setText(todo.title)
        todoEditText2.setText(todo.number)
        todoEditText3.setText(todo.address)
        calendarView.date = todo.date

        //완료 버튼을 클릭하면 수정
        doneFab.setOnClickListener {
            updateTodo(id)
        }

        //삭제 버튼을 클릭하면 삭제
        deleteFab.setOnClickListener {
            deleteTodo(id)
        }
    }
}
