package com.example.stopwatch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.concurrent.timer

class MainActivity : AppCompatActivity() {
    private var time = 0    //시간을 계산할 변수를 0으로 초기화
    
    //Timer 타입의  timerTask를 null을 허용하도록 선언
    //추후에 timer를 취소하려면 timer를 실행하고 반환되는 값을 저장할 필요가 있음
    private var timerTask: Timer? = null
    
    private var isRunning = false
    private var lap = 1 //추가되는 랩 타임 번호

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startFab.setOnClickListener{
            isRunning = !isRunning

            if(isRunning){
                start()
            }else{
                pause()
            }
        }

        lapButton.setOnClickListener{
            recordLapTime()
        }

        resetFab.setOnClickListener{
            reset()
        }
    }

    private fun start(){
        startFab.setImageResource(R.drawable.ic_pause_black_24dp)   //일시정지 이미지로 변경

        //0.01초마다 작업하는 timer
        timerTask = timer(period = 10){
            time++  //time변수를 1씩 증가
            val sec = time /100
            val milli = time % 100
            runOnUiThread{  //UI갱신
                secTextView.text = "$sec"
                milliTextView.text = "$milli"
            }
        }
    }

    private fun pause(){
        startFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)  //시작 이미지로 교체

        timerTask?.cancel() //타이머 취소
    }

    private fun recordLapTime(){
        val lapTime = this.time //현재 시간 저장
        val textView = TextView(this)   //텍스트뷰 생성
        textView.text = "$lap LAB : ${lapTime/100}.${lapTime%100}"  //1 LAB 5.35처럼 출력

        //맨 위에 랩타임 추가
        lapLayout.addView(textView,0)   //리니어 레이아웃에 랩 타임 추가
        lap++   //랩 타임 번호 증가
    }

    private fun reset(){
        timerTask?.cancel() //실행중인 타이머 취소

        //모든 변수 초기화 (화면에 보이는 모든 정보 초기화)
        time = 0
        isRunning = false
        startFab.setImageResource(R.drawable.ic_play_arrow_black_24dp)
        secTextView.text = "0"
        milliTextView.text = "00"
        
        //모든 랩타임을 제거
        lapLayout.removeAllViews()
        lap = 1
    }
}
