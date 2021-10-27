package com.example.sensordata2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter

class TodoListAdapter(realmResult: OrderedRealmCollection<Todo1>):
    RealmBaseAdapter<Todo1>(realmResult) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val view: View

        if(convertView == null){
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_todo, parent, false)
            vh = ViewHolder(view)
            view.tag = vh
        }else{
            view = convertView
            vh = view.tag as ViewHolder
        }

        if(adapterData != null){
            val item = adapterData!![position]
            vh.textTextView1.text = "이름 : "+item.name
            vh.textTextView2.text = "파워 : "+item.power
            vh.textTextView3.text = "RES : "+item.res
            vh.textTextView4.text = "RANGE : "+item.range
        }
        return view
    }

    override fun getItemId(position: Int): Long {
        if(adapterData != null){
            return adapterData!![position].id
        }
        return super.getItemId(position)
    }
}

class ViewHolder(view: View){
    val textTextView1: TextView = view.findViewById(R.id.text1)
    val textTextView2: TextView = view.findViewById(R.id.text2)
    val textTextView3: TextView = view.findViewById(R.id.text3)
    val textTextView4: TextView = view.findViewById(R.id.text4)
}