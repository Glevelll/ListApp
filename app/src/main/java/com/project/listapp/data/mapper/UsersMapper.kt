package com.project.listapp.data.mapper

import com.project.listapp.data.local.UsersEntity
import com.project.listapp.data.remote.UsersDto
import com.project.listapp.domain.User
import kotlin.math.log

fun UsersDto.toUsersEntity(): UsersEntity {
    return UsersEntity(
        email = email,
        gender = gender,
        firstName = name.first,
        lastName = name.last,
        city = location.city,
        country = location.country,
        pic = picture.medium
    )
}

fun UsersEntity.toUser(): User {
    return User(
        email = email,
        gender = gender,
        firstName = firstName,
        lastName = lastName,
        city = city,
        country = country,
        pic = pic
    )
}