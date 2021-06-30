package com.example.mywebbrowser

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.webkit.WebViewClient
import androidx.core.view.inputmethod.EditorInfoCompat
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.browse
import org.jetbrains.anko.email
import org.jetbrains.anko.sendSMS
import org.jetbrains.anko.share

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView.apply {
            //웹뷰 기본설정- 아래 2가지는 반드시 설정
            //1.자바 스크립트를 동작시키도록 javaScriptEnabled 기능 활성화
            settings.javaScriptEnabled = true
            //2.webViewClient를 클래스로 지정하지 않으면 웹뷰가 아니고 자체 웹 브라우저가 동작
            webViewClient = WebViewClient()
        }

        //loadUrl() 메서드를 사용해 "http:"이 포함된 Url을 전달하면 웹뷰에 해당 페이지가 로딩
        webView.loadUrl("http://www.google.com")

        //반응하는 인자로 뷰,액션Id,이벤트 (사용하지 않으면 _로 표기)
//        urlEditText.setOnEditorActionListener { _, actionId, _ ->   //에딧텍스트에 글자가 입력될 때마다 호출
//            //android값이 EditorInfo클래스의 검색 버튼에 해당하는 상수와 비교해 검색 버튼이 눌렸는지 확인
//            if(actionId == EditorInfo.IME_ACTION_SEARCH){
//                //검색창에 입력한 주소를 웹뷰에 전달해 로딩하고 true를 반환하여 종료
//                webView.loadUrl(urlEditText.text.toString())
//                true
//            }else{
//                false
//            }
//        }

        searchButton.setOnClickListener{
            webView.loadUrl(urlEditText.text.toString())
        }

        //registerForContextMenu()메서드에 컨텍스트 메뉴를 표시할 뷰로 웹뷰를 지정
        //앱을 실행한 후 해당 웹뷰를 롱클릭하면 컨텍스트 메뉴가 표시됨
        registerForContextMenu(webView)
    }

    override fun onBackPressed() {
        if(webView.canGoBack()){    //웹뷰가 이전페이지로 갈 수 있다면
            webView.goBack()    //이전 페이지로 이동
        } else{
            super.onBackPressed()   //그럴 수 없다면 원래 동작을 수행(종료)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu) //menuInflater객체의 inflate()메서드를 사용해 리소스 지정
        return true //ture를 반환하면 액티비티에 메뉴가 있음을 알림
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){ //메뉴 아이템으로 분기
            R.id.action_google, R.id.action_home -> {
                webView.loadUrl("http://www.google.com")
                return true //각 메뉴 처리를 끝나면 true 반환
            }
            R.id.action_naver -> {
                webView.loadUrl("http://www.naver.com")
                return true
            }
            R.id.action_daum -> {
                webView.loadUrl("http://www.daum.net")
                return true
            }
            R.id.action_call -> {   //암시적 인텐트를 이용해 연락처를 클릭하면 전화를 연결
                val intent = Intent(Intent.ACTION_DIAL)
                intent.data = Uri.parse("tel:031-123-4567")
                if(intent.resolveActivity(packageManager) != null){
                    startActivity(intent)
                }
                return true 
            }
            R.id.action_send_text -> {
                sendSMS("031-123-4567", webView.url)
                return true
            }
            R.id.action_email -> {
                email("test@example.com", "좋은 사이트", webView.url)
                return true
            }
        }
        //when문에서 분기하지 않은 예외의 경우에는 super메서드를 호출하는 것이 안드로이드 시스템의 규칙
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateContextMenu(menu: ContextMenu?, v: View?, menuInfo: ContextMenu.ContextMenuInfo?) {
        super.onCreateContextMenu(menu, v, menuInfo)
        //menuInflater.inflate()메서드로 메뉴 리소스를 컨텍스트 메뉴로 사용
        menuInflater.inflate(R.menu.context, menu)
    }

    override fun onContextItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.action_share -> {
                share(webView.url)
            }
            R.id.action_browser -> {
                browse(webView.url)
            }
        }
        return super.onContextItemSelected(item)
    }
}
