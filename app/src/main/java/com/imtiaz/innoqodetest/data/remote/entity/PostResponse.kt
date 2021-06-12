package com.imtiaz.innoqodetest.data.remote.entity

import com.google.gson.annotations.SerializedName

data class PostResponse(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("featured")
	val featured: Boolean? = null,

	@field:SerializedName("jetpack_featured_media_url")
	val jetpackFeaturedMediaUrl: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("title")
	val title: Title? = null,

	@field:SerializedName("content")
	val content: Content? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("date_gmt")
	val dateGmt: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null,

	@field:SerializedName("author")
	val author: Int? = null
)

data class Content(

	@field:SerializedName("rendered")
	val rendered: String? = null,

	@field:SerializedName("protected")
	val jsonMemberProtected: Boolean? = null
)


data class Title(

	@field:SerializedName("rendered")
	val rendered: String? = null
)

