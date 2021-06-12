package com.imtiaz.innoqodetest.ui.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.imtiaz.innoqodetest.data.local.entity.BlogPost
import com.imtiaz.innoqodetest.data.repository.HomeRepository
import com.imtiaz.taskmanager.utils.Resource
import kotlinx.coroutines.launch

class HomeViewModel: ViewModel() {

    val postsResponse: MutableLiveData<Resource<List<BlogPost>>> by lazy { MutableLiveData() }
    val repository: HomeRepository = HomeRepository()

    init {
        viewModelScope.launch {
            repository.getPosts(postsResponse)
        }
    }
}