package com.example.myapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapp.databinding.ActivityJoinBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityJoinBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        auth = Firebase.auth

        binding.joinButton.setOnClickListener() {
            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()
            val confirmPassword = binding.confirmPasswordArea.text.toString()

            var isGoToJoin = true

            // 회원가입 시 이메일 값이 비어있을 때
            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            // 회원가입 시 비밀번호 값이 비어있을 때
            if (password.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            // 회원가입 시 비밀번호 확인 값이 비어있을 때
            if (confirmPassword.isEmpty()) {
                Toast.makeText(this, "비밀번호 확인을 해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            // 비밀번호 길이가 6자 이하일 때
            if (password.length < 6) {
                Toast.makeText(this, "비밀번호를 6자리 이상 입력해 주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }
            // 비밀번호와 비밀번호 확인 값이 같지 않을 때
            if (password != confirmPassword) {
                Toast.makeText(this, "비밀번호를 동일하게 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

            if (isGoToJoin) {
                auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) { // 회원가입 성공 시
                        Toast.makeText(this, "회원가입 성공", Toast.LENGTH_LONG).show()
                        // 회원가입 성공 후 메인 화면으로 이동
                        val intent = Intent(this, MainActivity::class.java)
                        // 회원가입 완료 후 뒤로가기 버튼을 눌렀을 때 앱 꺼짐
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                    else { // 회원가입 실패 시
                        Toast.makeText(this, "회원가입 실패", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }

    }
}