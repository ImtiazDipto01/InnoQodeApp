package com.imtiaz.innoqodetest.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.imtiaz.innoqodetest.data.local.entity.BlogPost

@Dao
interface PostDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: BlogPost)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(post: List<BlogPost>)

    @Query("select * from post")
    suspend fun getAllPosts() : List<BlogPost>
}