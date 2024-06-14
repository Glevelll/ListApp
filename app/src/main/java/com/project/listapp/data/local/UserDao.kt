package com.project.listapp.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface UserDao {

    @Upsert
    suspend fun upsertAll(users: List<UsersEntity>)

    @Query("SELECT * FROM usersentity")
    fun pagingSource(): PagingSource<Int, UsersEntity>

    @Query("DELETE FROM usersentity")
    suspend fun clearAll()

    @Query("SELECT COUNT(*) FROM usersentity")
    fun getCount(): Int

}