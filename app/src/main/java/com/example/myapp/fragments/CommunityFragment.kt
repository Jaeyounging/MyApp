package com.example.myapp.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.navigation.findNavController
import com.example.myapp.R
import com.example.myapp.community.CommunityInsideActivity
import com.example.myapp.community.CommunityListAdapter
import com.example.myapp.community.CommunityModel
import com.example.myapp.community.CommunityWriteActivity
import com.example.myapp.databinding.FragmentCommunityBinding
import com.example.myapp.util.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class CommunityFragment : Fragment() {
    private lateinit var binding : FragmentCommunityBinding

    private val communityDataList = mutableListOf<CommunityModel>() // 파이어베이스로부터 받아온 데이터 저장 변수
    private val communityKeyList = mutableListOf<String>() // 파이어베이스로부터 받아온 키값 저장 변수

    private val TAG = CommunityFragment::class.java.simpleName
    private lateinit var communityRVAdapter : CommunityListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = inflate(inflater, R.layout.fragment_community, container, false)

        // 커뮤니티프레그먼트와 커뮤니티리스트 연결
        communityRVAdapter = CommunityListAdapter(communityDataList)
        binding.communityList.adapter = communityRVAdapter

        // 게시물 불러오기 - Firebase의 community에 대한 데이터의 id를 기반으로 데이터를 불러옴
        binding.communityList.setOnItemClickListener() { parent, view, position, id ->
            val intent = Intent(context, CommunityInsideActivity::class.java)
            intent.putExtra("key", communityKeyList[position]) // 키값 넘기기
            startActivity(intent)
        }

        // 글쓰기 버튼 눌렀을 때
        binding.writeBtn.setOnClickListener() {
            val intent = Intent(context, CommunityWriteActivity::class.java)
            startActivity(intent)
        }

        // 홈 버튼 눌렀을 때
        binding.homeTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_communityFragment_to_homeFragment)
        }

        // 검색 버튼 눌렀을 때
        binding.searchTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_communityFragment_to_searchFragment)
        }

        // 문의 버튼 눌렀을 때
        binding.inquiryTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_communityFragment_to_inquiryFragment)
        }

        // 마이페이지 버튼 눌렀을 때
        binding.mypageTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_communityFragment_to_myPageFragment)
        }

        getFBCommunityData()

        return binding.root
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