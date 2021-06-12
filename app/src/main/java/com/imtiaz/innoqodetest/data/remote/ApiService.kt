package com.example.mygalleryapp.data.api

import com.imtiaz.innoqodetest.data.remote.entity.PostResponse
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    suspend fun getPosts() : Response<List<PostResponse>>
}