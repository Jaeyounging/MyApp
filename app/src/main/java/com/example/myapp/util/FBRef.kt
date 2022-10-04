package com.example.myapp.util

import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FBRef {
    companion object {
        private val database = Firebase.database

        val communityRef = database.getReference("community")
    }
}