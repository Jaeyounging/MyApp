package com.example.myapp.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.myapp.R
import com.example.myapp.databinding.ActivityCommunityBinding
import com.example.myapp.util.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class CommunityActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCommunityBinding

    private val communityDataList = mutableListOf<CommunityModel>() // 파이어베이스로부터 받아온 데이터 저장 변수
    private val communityKeyList = mutableListOf<String>() // 파이어베이스로부터 받아온 키값 저장 변수

    private val TAG = CommunityActivity::class.java.simpleName
    private lateinit var communityRVAdapter : CommunityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_community)

        // 커뮤니티프레그먼트와 커뮤니티리스트 연결
        communityRVAdapter = CommunityListAdapter(communityDataList)
        binding.communityList.adapter = communityRVAdapter

        // 게시물 불러오기 - Firebase의 community에 대한 데이터의 id를 기반으로 데이터를 불러옴
        binding.communityList.setOnItemClickListener() { parent, view, position, id ->
            val intent = Intent(this, CommunityInsideActivity::class.java)
            intent.putExtra("key", communityKeyList[position]) // 키값 넘기기
            startActivity(intent)
        }

        // 글쓰기 버튼 눌렀을 때
        binding.writeBtn.setOnClickListener() {
            val intent = Intent(this, CommunityWriteActivity::class.java)
            startActivity(intent)
        }

        getFBCommunityData()
    }

    // 파이어베이스에서 데이터 들고 오기
    private fun getFBCommunityData() {
        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot : DataSnapshot) {
                communityDataList.clear() // 파이어베이스에 겹치지 않게 초기화

                for (dataModel in dataSnapshot.children) {
                    Log.d(TAG, dataModel.toString())

                    val item = dataModel.getValue(CommunityModel::class.java) // 커뮤니티 모델 형태로 파이어베이스로부터 데이터 받기
                    communityDataList.add(item!!)
                    communityKeyList.add(dataModel.key.toString()) // 데이터모델의 키값 넣어주기
                }

                communityKeyList.reverse()
                communityDataList.reverse()
                communityRVAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터 불러오기 실패할 경우 메세지 출력
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }

        FBRef.communityRef.addValueEventListener(postListener)
    }
}