package com.imtiaz.innoqodetest.data.local.entity

import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "post")
data class BlogPost(

    @PrimaryKey
    @ColumnInfo(name = "id")
    @NonNull
    val id: Int,

    @Nullable
    @ColumnInfo(name = "date")
    val date: String? = null,

    @Nullable
    @ColumnInfo(name = "title")
    val title: String? = null,

    @Nullable
    @ColumnInfo(name = "content")
    val content: String? = null,

    @Nullable
    @ColumnInfo(name = "img_url")
    val imageUrl: String? = null

)
