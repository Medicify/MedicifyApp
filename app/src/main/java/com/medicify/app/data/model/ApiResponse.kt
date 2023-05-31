package com.medicify.app.data.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(

	@field:SerializedName("request")
	val request: RequestResponse?,

	@field:SerializedName("service")
	val service: String,

	@field:SerializedName("response")
	val response: DrugsResponse,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)