package com.example.todolist_realm2

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val realmResult = realm.where<Todo>().findAll().sort("date", Sort.DESCENDING)
        //TodoListAdapter 클래스에 할 일 목록인 realmResult를 전달해 어댑터 인스턴스 생성
        val adapter = TodoListAdapter(realmResult)
        //생성한 어댑터를 리스트 뷰에 설정하면 할 일 목록이 리스트 뷰에 표시돔
        listView.adapter = adapter

        //데이터가 변경되면 어댑터에 적용
        //addChangeListener를 구현하면 데이터가 변경될 때마다 어댑터에게 알림
        //어댑터에 notifyDataSetChanged() 메서드를 호출하면 데이터 변경을 통지해 리스트를 다시 표시
        realmResult.addChangeListener {_ -> adapter.notifyDataSetChanged() }

        //EditActivity에 선택한 아이템의 id 값을 전달
        //기존 id가 있는지 여부에 따라 새 할 일을 추가하거나 기존 할 일을 수정할 수 있음
        listView.setOnItemClickListener { parent, view, position, id ->
            startActivity<EditActivity>("id" to id)
        }

        addFab.setOnClickListener {
            startActivity<EditActivity>()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }
}
