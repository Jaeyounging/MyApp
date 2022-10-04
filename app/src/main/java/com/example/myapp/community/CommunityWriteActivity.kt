package com.example.myapp.community

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.myapp.R
import com.example.myapp.databinding.ActivityCommunityWriteBinding
import com.example.myapp.util.FBAuth
import com.example.myapp.util.FBRef

class CommunityWriteActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCommunityWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_community_write)

        // 작성 완료 버튼을 눌렀을 때
        binding.writeFinishBtn.setOnClickListener() {
            val title = binding.titleArea.text.toString()
            val content = binding.contentArea.text.toString()
            val uid = FBAuth.getUId()
            val time = FBAuth.getTime()

            FBRef.communityRef
                .push()
                .setValue(CommunityModel(title, content, uid, time))

            Toast.makeText(this, "게시글 등록 완료", Toast.LENGTH_LONG).show()

            finish()
        }

        // 이미지 플러스 버튼을 눌렀을 때
        binding.plusImageBtn.setOnClickListener() {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, 100)
        }

    }

    override fun onActivityResult(requestCode : Int, resultCode : Int, data : Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == 100) {
            binding.imageArea.setImageURI(data?.data)
        }
    }
}