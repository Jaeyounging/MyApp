package com.example.myapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapp.auth.IntroActivity
import com.example.myapp.community.CommunityActivity
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.inquiry.ChatActivity
import com.example.myapp.inquiry.InquiryActivity
import com.example.myapp.mypage.MypageActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.naver.maps.map.*
import com.naver.maps.map.util.FusedLocationSource

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
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
    }

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 100
    }
}
