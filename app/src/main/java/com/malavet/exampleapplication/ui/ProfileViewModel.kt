package com.malavet.exampleapplication.ui.profile

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.*
import com.malavet.exampleapplication.data.ProfilesRepository
import com.malavet.exampleapplication.data.models.Profile
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

/*
* [ProfileUiState] keeps state of loading the Profile list frome the /users endpoint
* */
sealed interface ProfileUiState {
    data class Success(val profileList: List<Profile>) : ProfileUiState
    object Error : ProfileUiState
    object Loading : ProfileUiState
}

/*
* [ProfileViewModel] the viewmodel for MVVM pattern,
* */
@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val profilesRepository: ProfilesRepository
) :ViewModel() {

    var profileUiState: ProfileUiState by mutableStateOf(ProfileUiState.Loading)
    private var _configState = mutableStateOf<List<String>>(listOf("name","photo","gender"))
    val configState: State<List<String>> = _configState
    val profiles = profilesRepository.profiles


    init {
        getProfileData()
        getLayoutConfig()
    }

    /*
    * Getting Profiles from the Repository
    * Exception and Loading Handled in Layouts
    * */
    fun getProfileData() {
        viewModelScope.launch {
            profileUiState = ProfileUiState.Loading
            profileUiState = try {
                ProfileUiState.Success(profilesRepository.getProfiles())
            } catch (e: Exception) {
                ProfileUiState.Error
            }
        }
    }

        /*
    * Getting LayoutConfig from Repository
    * Default list in case of Exception
    * Data is not stored in database, a default list is used instead
    * */
      fun getLayoutConfig(){
         viewModelScope.launch {
             try {
                 _configState.value = profilesRepository.dataConfig().profile
             } catch (e: Exception) {
                 _configState.value = listOf("name", "gender", "photo")
             }
         }
    }
}









