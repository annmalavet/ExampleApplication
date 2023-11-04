package com.malavet.exampleapplication.data.models

import com.malavet.data.local.database.ProfileEntity
import kotlinx.serialization.Serializable

@Serializable
data class Profile (
    val id: Int,
    val name: String,
    val gender: String,
    val hobbies: List<String>? = null,
    val photo: String? = null,
    val about: String? = null,
    val school: String? = null
    )

@kotlinx.serialization.Serializable
data class Users (
  val users:  List<Profile>
)

//Converting Profile to Entity
fun Profile.asEntity() = ProfileEntity(
    id = id,
    name = name,
    gender =gender,
    hobbies  = hobbies,
    photo = photo,
    about = about,
    school  = school
)
