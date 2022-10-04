package com.example.myapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import com.example.myapp.R
import com.example.myapp.databinding.FragmentInquiryBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class InquiryFragment : Fragment() {
    private lateinit var binding : FragmentInquiryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inquiry, container, false)

        binding.homeTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_inquiryFragment_to_homeFragment)
        }

        binding.communityTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_inquiryFragment_to_communityFragment)
        }

        binding.searchTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_inquiryFragment_to_searchFragment)
        }

        binding.mypageTap.setOnClickListener() {
            it.findNavController().navigate(R.id.action_inquiryFragment_to_myPageFragment)
        }

        return binding.root
    }

}