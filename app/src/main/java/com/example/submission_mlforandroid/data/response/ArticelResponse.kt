package com.example.submission_mlforandroid.data.response

import com.google.gson.annotations.SerializedName

data class ArticelResponse(

	@field:SerializedName("totalResults")
	val totalResults: Int,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem>,

	@field:SerializedName("status")
	val status: String
)