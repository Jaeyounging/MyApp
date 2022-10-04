package com.example.myapp.util

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class FBAuth {
    companion object {
        private lateinit var auth: FirebaseAuth

        // 유저 아이디 반환 함수
        fun getUId() : String {
            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()
        }

        // 시간 반환 함수
        fun getTime() : String {
            val currentDateTime = Calendar.getInstance().time

            // 시간 가져오는 형태
            val dateFormat = SimpleDateFormat("yyyy.MM.dd. HH:mm:ss", Locale.KOREA).format(currentDateTime)

            return dateFormat
        }
    }
}