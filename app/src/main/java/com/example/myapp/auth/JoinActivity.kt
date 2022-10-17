package com.example.myapp.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapp.MainActivity
import com.example.myapp.R
import com.example.myapp.databinding.ActivityJoinBinding
import com.example.myapp.inquiry.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var dBRef: DatabaseReference
    private lateinit var binding: ActivityJoinBinding

//    private lateinit var name: EditText
//    private lateinit var emil: EditText
//    private lateinit var password: EditText
//    private lateinit var confirmPassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_join)
        auth = Firebase.auth

        binding.joinButton.setOnClickListener() {
            val name = binding.nameArea.text.toString()
            val email = binding.emailArea.text.toString()
            val password = binding.passwordArea.text.toString()
            val confirmPassword = binding.confirmPasswordArea.text.toString()

            var isGoToJoin = true

            // 회원가입 시 이름 값이 비어있을 때
            if (name.isEmpty()) {
                Toast.makeText(this, "이름을 입력해주세요.", Toast.LENGTH_LONG).show()
                isGoToJoin = false
            }

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
                signUp(name, email, password)
            }
        }

    }

    private fun signUp(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) { // 회원가입 성공 시
                Toast.makeText(this, "회원가입 성공", Toast.LENGTH_LONG).show()
                // 회원가입 성공 후 데이터베이스에 저장
                addUserToDatabase(name, email, auth.uid!!)
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

    // 회원가입 시 등록한 유저 정보를 파이어베이스 리얼타임 데이터베이스에 추가하는 함수
    private fun addUserToDatabase(name: String, email: String, uid: String) {
        dBRef = FirebaseDatabase.getInstance().getReference()
        dBRef.child("user").child(uid).setValue(User(name, email, uid))
    }
}