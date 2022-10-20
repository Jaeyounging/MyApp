package com.example.myapp.mypage

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.myapp.R
import com.example.myapp.databinding.ActivityMypageBinding
import com.google.firebase.auth.FirebaseAuth


class MypageActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityMypageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_mypage)

//        // 로그아웃 버튼 이벤트 처리
//        findViewById<Button>(R.id.logoutButton).setOnClickListener() {
//            auth.signOut()
//
//            // 로그아웃 시 인트로 화면으로 이동
//            val intent = Intent(this, IntroActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(intent)
    }
}
