package com.medicify.app.data.model

import com.google.gson.annotations.SerializedName

data class TitleRequestForm(
    @field:SerializedName("title")
    val title: String,

//    @field:SerializedName("score")
//    val score: Int = 90,

)
