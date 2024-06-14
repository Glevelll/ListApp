package com.project.listapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class UsersEntity(
    @PrimaryKey
    val email: String,
    val gender: String,
    val firstName: String,
    val lastName: String,
    val city: String,
    val country: String,
    val pic: String?
)