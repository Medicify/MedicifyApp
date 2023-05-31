package com.medicify.app.data.model

import com.google.gson.annotations.SerializedName

data class RequestResponse(

    @field:SerializedName("id")
    val id: String,

    @field:SerializedName("title")
    val title: String,

    @field:SerializedName("category")
    val category: String

)
