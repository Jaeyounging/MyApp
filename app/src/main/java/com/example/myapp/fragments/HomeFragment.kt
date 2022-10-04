package com.example.myapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentHomeBinding
import net.daum.mf.map.api.MapView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeFragment : Fragment() {
    private lateinit var binding : FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)

        // 커뮤니티 버튼 눌렀을 때
        binding.communityTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_homeFragment_to_communityFragment)
        }

        // 검색 버튼 눌렀을 때
        binding.searchTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_homeFragment_to_searchFragment)
        }

        // 문의 버튼 눌렀을 때
        binding.inquiryTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_homeFragment_to_inquiryFragment)
        }

        // 마이페이지 버튼 눌렀을 때
        binding.mypageTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_homeFragment_to_myPageFragment)
        }

//        // 카카오맵 띄우기
//        val mapView = MapView(context)
//        binding.mapView.addView(mapView)

        return binding.root
    }


}
