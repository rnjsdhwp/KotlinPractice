package com.example.sensordata2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.realm.Realm
import io.realm.Sort
import io.realm.kotlin.where

import kotlinx.android.synthetic.main.content_main.*
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity() {
    val realm = Realm.getDefaultInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val realmResult = realm.where<Todo1>().findAll().sort("name", Sort.DESCENDING)
        val adapter = TodoListAdapter(realmResult)
        listView.adapter = adapter

        listView.setOnItemClickListener { parent, view, position, id ->
            startActivity<EditActivity>("id" to id)
        }

        addFab.setOnClickListener {
            startActivity<EditActivity>()
        }
    }

    override fun onResume() {
        super.onResume()
        if(realm.where<Todo1>().max("id").toString() != "null"){
            var a = realm.where<Todo1>().max("id")?.toInt()!! +1
            resultNum.text = "센서 개수 : $a"
        }else{
            resultNum.text = "센서 개수 : 0"
        }
        //var a = realm.where<Todo1>().max("id")?.toInt()!! +1
        //resultNum.text = "센서 개수 : $a"
    }

    override fun onDestroy() {
        super.onDestroy()
        realm.close()
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
