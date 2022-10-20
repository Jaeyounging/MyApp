package com.example.myapp.retrofit2

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class EvDto(
    @Expose
    @SerializedName("page")
    val page: Int, // 경도
    @Expose
    @SerializedName("perPage")
    val perPage: Int, // 경도
    @Expose
    @SerializedName("totalCount")
    val totalCount: Int, // 경도
    @Expose
    @SerializedName("matchCount")
    val matchCount: Int, // 경도
    @Expose
    @SerializedName("data")
    val data: List<Data>
)

data class Data(
    @Expose
    @SerializedName("csId")
    val csId: Int, // 충전기 ID
    @Expose
    @SerializedName("csNm")
    val csNm: String, // 충전소 명칭
    @Expose
    @SerializedName("addr")
    val addr: Double, // 충전소 주소
    @Expose
    @SerializedName("lat")
    val lat: Double, // 위도
    @Expose
    @SerializedName("longi")
    val longi: Double, // 경도
    @Expose
    @SerializedName("cpId")
    val cpId: Int, // 충전기ID
    @Expose
    @SerializedName("cpNm")
    val cpNm: Double, // 충전기 명칭
    @Expose
    @SerializedName("cpTp")
    val cpTp: Double, // 충전기 타입
)
