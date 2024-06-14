package com.project.listapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UsersEntity::class],
    version = 1
)

abstract class UsersDatabase: RoomDatabase() {

    abstract val dao: UserDao
}