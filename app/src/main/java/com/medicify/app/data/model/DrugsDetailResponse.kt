package com.medicify.app.data.model

import com.google.gson.annotations.SerializedName

data class DrugsDetailResponse(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("data")
	val data: DrugItem,

)