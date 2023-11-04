package com.malavet.exampleapplication.data

import com.malavet.data.local.database.ProfileEntity
import com.malavet.exampleapplication.data.database.DataDao
import com.malavet.exampleapplication.data.models.LayoutOrder
import com.malavet.exampleapplication.data.models.Profile
import com.malavet.exampleapplication.network.NetworkService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.collections.map
import javax.inject.Inject

class ProfilesRepository @Inject constructor(
    private val apiService: NetworkService,
    private val dataDao: DataDao
) {

    var profiles: List<Profile> = emptyList()

    /*
    * Get the list of Profiles from the database, only called when network call fails
    */
    suspend fun databaseProfiles(): List<Profile>{
            withContext(Dispatchers.IO) {
            profiles= dataDao.getAllProfiles()
         }
        return profiles
    }

    /*
    * Get the layout configuration list
    */
    suspend fun dataConfig(): LayoutOrder {
        val s : LayoutOrder
        withContext(Dispatchers.IO) {
          s =   apiService.profileApi.getDataTest()
        }
        return s
    }

    /*
    * If the network call fails, get the Profiles from the database
    */
    suspend fun getProfiles(): List<Profile> {
        var listOfProfiles: List<Profile>
        withContext(Dispatchers.IO) {
            try{
                listOfProfiles = apiService.profileApi.getAll().users
                val response = apiService.profileApi.getAll().users
                // Convert the data models to entities and insert into database
                val entities = response.map { d -> mapDataModelToEntity(d) }
                dataDao.insertOrIgnoreProfile(entities)}
            catch (e: Exception) {
                listOfProfiles = databaseProfiles()
            }
        }
        return listOfProfiles
    }

    /*
    * Mapping Profile to Entity type so it can be stored in the database
    * Possibly should move this to Data layer/directory
    */
    private fun mapDataModelToEntity(dataModel: Profile): ProfileEntity {
        return ProfileEntity(
            id = dataModel.id,
            name = dataModel.name,
            gender = dataModel.gender,
            hobbies  = dataModel.hobbies,
            photo = dataModel.photo,
            about = dataModel.about,
            school  = dataModel.school
        )
    }
}