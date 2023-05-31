package com.medicify.app.data.model

import com.google.gson.annotations.SerializedName

data class DrugsResponse(

	@field:SerializedName("total")
	val total: Int,

	@field:SerializedName("data")
	val data: List<DrugItem>
)