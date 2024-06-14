package com.project.listapp.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.project.listapp.data.local.UsersDatabase
import com.project.listapp.data.local.UsersEntity
import com.project.listapp.data.mapper.toUsersEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okio.IOException
import retrofit2.HttpException

@OptIn(ExperimentalPagingApi::class)
class UserRemoteMediator(
    private val usersDB: UsersDatabase,
    private val usersApi: UsersApi
) : RemoteMediator<Int, UsersEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UsersEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        val totalCount = withContext(Dispatchers.IO) {
                            usersDB.dao.getCount()
                        }
                        (totalCount / state.config.pageSize) + 1
                    }
                }
            }

            delay(2000L)
            val response = usersApi.getUsers(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            if (response.isSuccessful) {
                val users = response.body()?.results ?: emptyList()

                usersDB.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        usersDB.dao.clearAll()
                    }
                    val usersEntities = users.map { it.toUsersEntity() }
                    usersDB.dao.upsertAll(usersEntities)
                }

                MediatorResult.Success(
                    endOfPaginationReached = users.isEmpty()
                )
            } else {
                MediatorResult.Error(IOException("Failed to load users: ${response.code()} ${response.message()}"))
            }

        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}
