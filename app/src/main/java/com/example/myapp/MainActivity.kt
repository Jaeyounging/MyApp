package com.example.myapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapp.auth.IntroActivity
import com.example.myapp.community.CommunityActivity
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.inquiry.InquiryActivity
import com.example.myapp.mypage.MypageActivity
import com.example.myapp.retrofit2.*
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.naver.maps.geometry.LatLng
import com.naver.maps.map.*
import com.naver.maps.map.overlay.Marker
import com.naver.maps.map.overlay.Overlay
import com.naver.maps.map.util.FusedLocationSource
import com.naver.maps.map.util.MarkerIcons
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), OnMapReadyCallback, Overlay.OnClickListener {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMainBinding

    private lateinit var locationSource: FusedLocationSource
    private lateinit var naverMap: NaverMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = Firebase.auth
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        // 요금 안내 버튼을 클릭했을 때

        // 커뮤니티 버튼을 클릭했을 때
        binding.communityTap.setOnClickListener() {
            val intent = Intent(this, CommunityActivity::class.java)
            startActivity(intent)
        }

        // 마이페이지 버튼을 클릭했을 때
        binding.mypageTap.setOnClickListener() {
            val intent = Intent(this, MypageActivity::class.java)
            startActivity(intent)
        }

        // 문의 버튼을 클릭했을 때
        binding.inquiryTap.setOnClickListener() {
            val intent = Intent(this, InquiryActivity::class.java)
            startActivity(intent)
        }

        // 네이버 지도
        NaverMapSdk.getInstance(this).client = NaverMapSdk.NaverCloudPlatformClient("4u7v3qgyl7")

        // 로그아웃 버튼 이벤트 처리
        findViewById<Button>(R.id.logoutBtn).setOnClickListener() {
            auth.signOut()

            // 로그아웃 시 인트로 화면으로 이동
            val intent = Intent(this, IntroActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        if (locationSource.onRequestPermissionsResult(requestCode, permissions,
                grantResults)) {
            if (!locationSource.isActivated) { // 권한 거부됨
                naverMap.locationTrackingMode = LocationTrackingMode.None
            }
            return
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    override fun onMapReady(naverMap: NaverMap) {
        this.naverMap = naverMap
        naverMap.locationSource = locationSource

        // 지도 로드 후 마커 띄우기
        getEvListFromAPI()
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }

    private fun getEvListFromAPI() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://infuser.odcloud.kr")
            .addConverterFactory(retrofit2.converter.gson.GsonConverterFactory.create())
            .build()

        retrofit.create(EvService::class.java).also {
            it.getEvList()
                .enqueue(object : Callback<EvDto> {
                    override fun onResponse(call: Call<EvDto>, response: Response<EvDto>) {
                        if (response.isSuccessful.not()) {
                            // fail
                            Log.d("Retrofit", "실패1")

                            return
                        }

                        // 성공한 경우 아래 처리
                        response.body()?.let { dto ->
                            updateMarker(dto.data)
//                            viewPagerAdapter.submitList(dto.data)
//                            recyclerViewAdapter.submitList(dto.data)
//                            bottomSheetTitleTextView.text = "${dto.data.size}개의 숙소"
                        }
                    }

                    override fun onFailure(call: Call<EvDto>, t: Throwable) {
                        // 실패 처리 구현;
                        Log.d("Retrofit", "실패2")
                        Log.d("Retrofit", t.stackTraceToString())
                    }

                })
        }
    }

    private fun updateMarker(houses: List<Data>) {
        houses.forEach { house ->

            val marker = Marker()
            marker.position = LatLng(house.lat, house.longi)
           // marker.onClickListener = this // 마커 클릭 시 뷰 페이져 연동 되도록 구현
            marker.map = naverMap
            marker.tag = house.addr
            marker.icon = MarkerIcons.BLACK
            marker.iconTintColor = Color.RED

        }
    }

    override fun onClick(p0: Overlay): Boolean {
        TODO("Not yet implemented")
    }
}