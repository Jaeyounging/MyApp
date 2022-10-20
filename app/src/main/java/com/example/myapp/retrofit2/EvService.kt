package com.example.myapp.retrofit2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface EvService {
    @GET("/service/EvInfoServiceV2")
    fun getEvList(
        @Query("page")
        page: Int = 1,
        @Query("numOfRows")
        perPage: Int = 10,
        @Query("returnType")
        returnType: String = "JSON"
    ): Call<EvDto>
}