package com.malavet.exampleapplication.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.malavet.data.local.database.ProfileEntity
import com.malavet.exampleapplication.data.models.Profile
import kotlinx.coroutines.flow.Flow

@Dao
interface DataDao {

    @Query("SELECT * FROM profile_entity")
    fun getAllProfiles(): List<Profile>

    /**
     * Inserts [profileEntities] into the db if they don't exist, and ignores those that do
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreProfile(profileEntities: List<ProfileEntity>): List<Long>

}