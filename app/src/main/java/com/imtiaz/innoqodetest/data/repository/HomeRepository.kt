package com.imtiaz.innoqodetest.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.mygalleryapp.data.api.ApiClient
import com.example.mygalleryapp.data.api.ApiService
import com.imtiaz.innoqodetest.App
import com.imtiaz.innoqodetest.data.local.AppDatabase
import com.imtiaz.innoqodetest.data.local.entity.BlogPost
import com.imtiaz.innoqodetest.data.remote.entity.PostResponse
import com.imtiaz.taskmanager.utils.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class HomeRepository {

    private val appDb: AppDatabase by lazy {
        AppDatabase.getInstance(App.getContext())
    }

    private val apiService: ApiService? by lazy { ApiClient.getApiService() }

    suspend fun getPosts(liveData: MutableLiveData<Resource<List<BlogPost>>>) {
        liveData.postValue(Resource.loading(null))

        val value = withContext(Dispatchers.IO) {
            try {
                val response = apiService?.getPosts()

                if (response != null && response.isSuccessful) response
                else null

            } catch (exp: Exception) {
                null
            }
        }
        if (value != null) {
            insertPosts(processPosts(value.body()), liveData)
        } else getPostsFromDb(liveData)
    }

    private fun processPosts(response: List<PostResponse?>?): List<BlogPost> {
        val list = mutableListOf<BlogPost>()
        response?.let {
            for (data in it) {
                data?.apply {
                    val post = BlogPost(
                        id ?: return@apply,
                        date, title?.rendered, content?.rendered, jetpackFeaturedMediaUrl
                    )
                    list.add(post)
                }
            }
        }
        return list
    }

    private fun insertPosts(
        postList: List<BlogPost>,
        liveData: MutableLiveData<Resource<List<BlogPost>>>
    ) {
        CoroutineScope(Dispatchers.Main).launch {

            if(postList.isNotEmpty()){
                val insertionProcessDone = withContext(Dispatchers.IO){
                    try {
                        appDb.postDao().insertAll(postList)
                        true
                    }
                    catch (exp: Exception){
                        true
                    }
                }
                if(insertionProcessDone){
                    getPostsFromDb(liveData)
                }
            }
            else getPostsFromDb(liveData)
        }
    }

    private fun getPostsFromDb(liveData: MutableLiveData<Resource<List<BlogPost>>>) {
        CoroutineScope(Dispatchers.Default).launch {
            try {
                val list = appDb.postDao().getAllPosts()
                liveData.postValue(Resource.success(list))
            }catch (exp: Exception){
                liveData.postValue(Resource.error(null))
            }
        }
    }
}