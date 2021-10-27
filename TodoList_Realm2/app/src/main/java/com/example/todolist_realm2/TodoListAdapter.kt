package com.example.todolist_realm2

import android.text.format.DateFormat
import android.view.View
import android.view.ViewGroup
import io.realm.OrderedRealmCollection
import io.realm.RealmBaseAdapter
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TodoListAdapter(realmResult: OrderedRealmCollection<Todo>) :
RealmBaseAdapter<Todo>(realmResult){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val vh: ViewHolder
        val view: View

        if(convertView == null){//convertView가 null이면 레이아웃을 작성
            //LayoutInflater클래스는 XML 레이아웃 파일을 코드로 불러오는 기능을 제공
            //LayoutInflater.from(parent?.content) 메서드로 객체를 얻고
            //inflate() 메서드로 XML 레이아웃 파일을 읽어서 뷰로 반환하여 view 변수에 할당
            view = LayoutInflater.from(parent?.context).inflate(R.layout.item_todo, parent, false)

            //뷰 홀더 객체를 초기화. 뷰 홀더 클래스는 별도의 클래스로 먼저 작성
            //뷰 홀더 클래스는 전달받은 view에서 text1과 text2 아이디를 가진 텍스트 뷰들의 참조를 저장하는 역할
            vh = ViewHolder(view)
            //뷰 홀더 객체는 tag 프로퍼티로 view에 저장됨
            //Tag 프로퍼티는 any 형으로 어떠한 객체도 저장할 수 있음
            view.tag = vh
        }else{//converView가 null이 아니라면
            view = convertView  //이전에 작성했던 convertView를 자새용
            vh = view.tag as ViewHolder //뷰홀더 객체를 tag프로퍼티로 꺼냄, 반환되는 데이터형이 Any이므로 ViewHolder로 형변환
        }

        if(adapterData != null){
            val item = adapterData!![position]  //해당 위치의 데이터를 item변수에 저장
            vh.textTextView1.text = "장비명 : "+item.title   //할 일 텍스트와 날짜를 각각 텍스트 뷰에 표시
            vh.textTextView2.text = "개수 : "+item.number
            vh.textTextView3.text = "배송지 : "+item.address
            //DateFormat.format()메서드는 지정한 형식으로 Long타입의 시간 데이터를 변환
            //DateFormat 클래스는 android.text.format.DateFormat을 임포트
            vh.dateTextView.text = "날짜 : "+DateFormat.format("MM/dd", item.date)
        }
        return view
    }

    override fun getItemId(position: Int): Long {
        if(adapterData != null){
            //adapterView가 Realm 데이터를 가지고 있으므로 요청한 해당 위치에 있는 데이터의 id값을 반환하도록 함
            return adapterData!![position].id
        }
        return super.getItemId(position)
    }
}

class ViewHolder(view: View){
    val dateTextView: TextView = view.findViewById(R.id.text1)
    val textTextView1: TextView = view.findViewById(R.id.text2)
    val textTextView2: TextView = view.findViewById(R.id.text3)
    val textTextView3: TextView = view.findViewById(R.id.text4)
}