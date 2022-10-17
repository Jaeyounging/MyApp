package com.example.myapp.community

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.myapp.R
import com.example.myapp.databinding.ActivityCommunityInsideBinding
import com.example.myapp.util.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class CommunityInsideActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityInsideBinding

    private val TAG = CommunityActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_inside)

        val key = intent.getStringExtra("key")

        getCommunityData(key.toString())
    }

    // 키값을 이용해 Firebase에서 게시글 불러오는 함수
    private fun getCommunityData(key : String) {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                Log.d(TAG, dataSnapshot.toString()) // 어떤 데이터인지 보기 위해
                val dataModel = dataSnapshot.getValue(CommunityModel::class.java) // CommunityModel 형태로 데이터 받아오기

                // 데이터 알맞은 레이아웃 위치에 넣어주기
                binding.titleArea.text = dataModel!!.title
                binding.contentArea.text = dataModel!!.content
                binding.timeArea.text = dataModel!!.time
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        FBRef.communityRef.child(key).addValueEventListener(postListener)
    }
}