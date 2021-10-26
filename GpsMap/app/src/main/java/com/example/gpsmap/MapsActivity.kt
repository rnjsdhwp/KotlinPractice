package com.example.gpsmap

import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager.LayoutParams.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.model.PolylineOptions
import org.jetbrains.anko.alert
import org.jetbrains.anko.noButton
import org.jetbrains.anko.yesButton
import java.util.jar.Manifest

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    private val REQUEST_ACCESS_FINE_LOCATION = 100
    //위치 정보를 주기적으로 얻는데 필요한 객체들을 선언
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    //MyLocationCallBack은 MapsActivity 클래스의 내부 클래스로 생성
    private lateinit var locationCallback: MyLocationCallBack
    private  val polylineOptions = PolylineOptions().width(5f).color(Color.RED)


    @SuppressLint("SourceLockedOrientationActivity")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //화면이 꺼지지 않게 하기
        window.addFlags(FLAG_KEEP_SCREEN_ON)
        //세로모드로 화면 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_maps)

        //supportFragment를 가져와서 지도가 준비되면 알림을 받음
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        
        locationInit()  //위에 선언한 변수들을 초기화
    }

    private fun locationInit(){
        fusedLocationProviderClient = FusedLocationProviderClient(this)

        locationCallback = MyLocationCallBack()

        locationRequest = LocationRequest()
        //GPS우선, LocationRequest는 위치 정보 요청에 대한 세부정보 설정
        locationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        //업데이트 인터벌
        //위치 정보가 없을 때는 업데이트 안함
        //상황에 따라 짧아질 수 있음, 정확하지 않음
        //다른 앱에서 짧은 인터벌로 위치 정보를 요청하면 짧아질 수 있음
        locationRequest.interval = 10000
        //정확함. 이것보다 짧은 업데이트는 하지 않음
        locationRequest.fastestInterval = 5000
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {//지도가 준비되면 GoogleMap 객체를 가져옴
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)   //호주 시드니에 마커를 추가하고 화면이동
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun onResume() {
        super.onResume()
        //권한요청
        permissinCheck(cancel = {
            //위치 정보가 필요한 이유 다이얼로그 표시
            showPermissionInfoDialog()
        }, ok = {
            //현재 위치를 주기적으로 요청 (권한이 필요한 부분)
            addLocationListener()   //별도의 메서드로 작성해 위치요청
        })
    }

    @SuppressLint("MissingPermission")
    private fun addLocationListener(){
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }

    override fun onPause() {
        super.onPause()
        removeLocationListener()
    }

    private fun removeLocationListener(){
        //현재 위치 요청을 삭제
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    //requestLocationUpdates() 메서드에 전달되는 인자 중
    //LocationCallBack을 구현한 내부 클래스는 LocationResult 객체를 반환하고 lastLocation 프로퍼티로 Location 객체를 얻음
    inner class MyLocationCallBack : LocationCallback(){
        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            
            val location = locationResult?.lastLocation
            
            //기기의 GPS설정이 꺼져있거나 현재 위치정보를 얻을 수 없는 경우에 Location 객체가 null일 수 있음
            //Location객체가 null이 아닐 때 해당 위도와 경도 위치로 카메라를 이동
            location?.run{
                // 14 level로 확대하며 현재 위치로 카메라 이동
                val latLng = LatLng(latitude, longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 17f))

                Log.d("MapsActivity", "위도: $latitude, 경도: $longitude")
                polylineOptions.add(latLng)
                //선 그리기
                mMap.addPolyline(polylineOptions)
            }
        }
    }

    //이 메서드는 함수 인자 두 개를 받음, 두 함수는 모두 인자와 반환 값이 없음
    private fun permissinCheck(cancel: () -> Unit, ok: () -> Unit){
        //위치 권한이 있는지 검사

        if(ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            //권한이 허용되지 않음
            if(ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.ACCESS_FINE_LOCATION)){
                //이전에 권한을 한 번 거부한 적이 있는 경우에 실행
                cancel()
            }else{
                //권한요청
                ActivityCompat.requestPermissions(this,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_ACCESS_FINE_LOCATION)
            }
        }else{
            //권한을 수락했을 때 실행
            ok()
        }
    }

    private fun showPermissionInfoDialog(){
        alert("현재 위치 정보를 얻기 위해서는 위치 권한이 필요합니다", "권한이 필요한 이유"){
            yesButton {
                //권한요청
                ActivityCompat.requestPermissions(this@MapsActivity,
                arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_ACCESS_FINE_LOCATION)
                //requestPermission() 메서드의 첫 번째 인자는 Context 또는 Activity를 전달
                //yesButton{} 블럭 내부에서 this는 DialogInterface 객체를 나타냄
                //액티비티를 명시적으로 가리키려면 this@MapsActivity로 작성해야 함
            }
            noButton {  }
        }.show()
    }
}
