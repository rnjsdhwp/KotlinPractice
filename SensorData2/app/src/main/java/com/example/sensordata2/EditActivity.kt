package com.example.sensordata2

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton

class EditActivity : AppCompatActivity() {
    //val realm by lazy {  Realm.getDefaultInstance()}
    val realm = Realm.getDefaultInstance()
    val sensorManager by lazy { getSystemService(Context.SENSOR_SERVICE) as SensorManager }
    var count = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit)

        val id = intent.getLongExtra("id", -1L)
        if(id == -1L){
            insertMode()
        }else{
            updateMode(id)
        }

//        var count = 1
//        var b = sensorManager.getSensorList(Sensor.TYPE_ALL)
//        for(i in 0..b.size-1){
//            var sen = b[i]
//            //Log.d("TAG", sen.toString())
//            //Log.d("count", count.toString())
//            insertTodo(sen.name, sen.power.toString(), sen.resolution.toString(), sen.maximumRange.toString())
//            count+=1
//        }
//
//        alert("내용이 추가되었습니다."){
//            yesButton { finish() }
//        }.show()
    }
    override fun onDestroy() {
        super.onDestroy()
        realm.close()
    }
//    private fun insertTodo(
//        name: String,
//        power: String,
//        resolution: String,
//        maximumRange: String
//    ) {
//        realm.beginTransaction()
//        val todo = realm.createObject<Todo1>(nextId())
//        todo.name = name
//        todo.power = power
//        todo.res =  resolution
//        todo.range = maximumRange
//        realm.commitTransaction()
//    }
    private fun insertMode(){
        deleteFab.visibility = View.GONE    //삭제 버튼을 감추기
        var b = sensorManager.getSensorList(Sensor.TYPE_ALL)
        //오류 발생시 deleteFab.hide()
        doneFab.setOnClickListener {
            for(i in 0..b.size-1) {
                var sen = b[i]
                insertTodo(sen)
            }
            alert("내용이 추가되었습니다."){
                yesButton { finish() }
            }.show()
        }
    }
    private fun insertTodo(sen: Sensor){
        realm.beginTransaction()    //트랜잭션 시작
        val todo = realm.createObject<Todo1>(nextId())   //새 객체 생성
        //var b = sensorManager.getSensorList(Sensor.TYPE_ALL)
        todo.name = sen.name
        todo.power = sen.power.toString()
        todo.res = sen.resolution.toString()
        todo.range = sen.maximumRange.toString()
        count+=1
        realm.commitTransaction()   //트랜잭션 종료

//        alert("내용이 추가되었습니다."){
//            yesButton { finish() }
//        }.show()    //다이얼로그 표시
    }
    private fun nextId(): Int{
        val maxId = realm.where<Todo1>().max("id")
        if(maxId != null){
            return maxId.toInt()+1
        }
        return 0
    }
    private fun updateTodo(id: Long){
        realm.beginTransaction()
        //!!: todo는 이후부터 null이 아님
        val todo = realm.where<Todo1>().equalTo("id", id).findFirst()!!
//        todo.title = todoEditText1.text.toString()
//        todo.number = todoEditText2.text.toString()
//        todo.address = todoEditText3.text.toString()
//        todo.date = calendar.timeInMillis
        realm.commitTransaction()

        alert("내용이 변경되었습니다.") {
            yesButton { finish() }
        }.show()
    }
    private fun deleteTodo(id: Long){
        realm.beginTransaction()
        val todo = realm.where<Todo1>().equalTo("id", id).findFirst()!!
        todo.deleteFromRealm()  //deleteFromRealm 메서드로 삭제
        realm.commitTransaction()

        alert("내용이 삭제되었습니다.") {
            yesButton { finish() }
        }.show()
    }
    private fun updateMode(id: Long){
        //id에 해당하는 객체를 화면에 표시
        val todo = realm.where<Todo1>().equalTo("id", id).findFirst()!!

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
