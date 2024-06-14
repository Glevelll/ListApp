package com.project.listapp.data.remote

data class UsersDto(
    val email: String,
    val gender: String,
    val name: UserName,
    val location: Location,
    val picture: Picture
)

data class UserName(
    val first: String,
    val last: String
)

data class Location(
    val city: String,
    val country: String
)

data class Picture(
    val medium: String
)

