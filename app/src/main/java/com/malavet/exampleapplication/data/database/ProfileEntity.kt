package com.malavet.data.local.database

import androidx.room.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.malavet.exampleapplication.data.models.Profile

@Entity
(tableName = "profile_entity")
data class ProfileEntity(
    @PrimaryKey
    val id: Int,
    val name: String,
    val gender: String,
    @TypeConverters(HobbiesListConverter::class)
    @ColumnInfo(defaultValue = "")
    val hobbies: List<String>?,
    @ColumnInfo(defaultValue = "")
    val photo: String?,
    @ColumnInfo(defaultValue = "")
    val about: String?,
    @ColumnInfo(defaultValue = "")
    val school: String?
)

fun ProfileEntity.asExternalModel() = Profile(
    id = id,
    name = name,
    gender = gender,
    hobbies = hobbies,
    photo = photo,
    about = about,
    school = school
)

class HobbiesListConverter {
    @TypeConverter
    fun fromGroupTaskMemberList(value: List<String>?): String {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toGroupTaskMemberList(value: String): List<String>? {
        val gson = Gson()
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson(value, type)
    }
}