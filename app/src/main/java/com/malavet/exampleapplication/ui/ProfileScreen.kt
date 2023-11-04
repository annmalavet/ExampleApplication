package com.malavet.exampleapplication.ui.profile

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.malavet.exampleapplication.R
import com.malavet.exampleapplication.data.models.Profile

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue


/*
* All layouts for this app are in this file
* */

/*
Entry point, shows Loading, Failed, or Profile Screen if data loaded successfully
 */
@Composable
fun EntryPoint(profileUiState: ProfileUiState, retryAction: () -> Unit, modifier: Modifier = Modifier, profileViewModel: ProfileViewModel = viewModel()
) {
    val myListState = remember { mutableStateOf(emptyList<String>()) }
    val networkResponse: List<String> =  profileViewModel.configState.value
    myListState.value = networkResponse

    when (profileUiState) {
        is ProfileUiState.Loading -> LoadingScreen(retryAction = { profileViewModel.getProfileData() }, modifier = modifier.fillMaxSize())
        is ProfileUiState.Success -> ProfilesScreen(profileUiState.profileList, myListState )
        else -> ErrorScreen(retryAction = { profileViewModel.getProfileData() }, modifier = modifier.fillMaxSize())
    }
}

/*
The list of Profiles
 */
@Composable
fun ProfilesScreen(profiles: List<Profile>, configuration: MutableState<List<String>>) {

    var currentUserIndex by remember { mutableStateOf(0) }
    val currentUser = profiles[currentUserIndex]

    OneProfileScreen(
        user = currentUser,
        configuration = configuration
    ) {
        if (currentUserIndex < profiles.size - 1) {
            currentUserIndex++
        } else {
            currentUserIndex = 0
        }
    }
}

/*
Singular Profile Layout
 */
@Composable
fun OneProfileScreen(user: Profile, configuration: MutableState<List<String>>, onNextUserClicked: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        configuration.value.forEach { field ->
            when (field) {
                "name" -> NameLayout(user.name)
                "photo" -> PhotoLayout(user.photo)
                "about" -> user.about?.let { NameLayout(it) }
                "school" -> user.school?.let { SchoolLayout(it) }
                "gender" -> GenderLayout(user.gender)
                "hobbies" -> user.hobbies?.let { HobbiesLayout(it) }
            }
        }
        Button(onClick = onNextUserClicked, modifier = Modifier.wrapContentSize(Alignment.Center)) {
            Text(text = "Next Profile")
            }
        }
    }

/*
* One Photo Layout
* */
    @Composable
    fun PhotoLayout(photoUrl: String?) {
        AsyncImage(
            model = ImageRequest.Builder(context = LocalContext.current).data(photoUrl)
                .crossfade(true).build(),
            error = painterResource(R.drawable.black_square),
            placeholder = painterResource(R.drawable.black_square),
            contentDescription = ("description of photo"),
            contentScale = ContentScale.Inside
    )
    }
/*
* Profile Text Layout
* */
    @Composable
    fun NameLayout(t: String) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text(t)
        }
    }
/*
* Profile Gender Layout
* */
    @Composable
    fun GenderLayout(t: String) {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Text("Gender: $t")
        }
    }
/*
* Profile School Layout
* */
    @Composable
    fun SchoolLayout(t: String) {
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = 8.dp,
            ){
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text("School: $t")
            }
        }
    }
/*
* Profile Hobbies Layout
* */
    @Composable
    fun HobbiesLayout(hobbies: List<String>) {
        Text("Hobbies: ")
        LazyColumn {
            items(hobbies) { string ->
                Text(text = string)
            }
        }
    }

    @Composable
    fun LoadingScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = ("Loading"), modifier = Modifier.padding(16.dp))
        }
    }

    @Composable
    fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
        Column(
            modifier = modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = androidx.core.R.drawable.ic_call_answer_video), contentDescription = ""
            )
            Text(text = ("Failed"), modifier = Modifier.padding(16.dp))
            Row {
                Button(onClick = { retryAction }) {
                    Text("Retry")
                }
            }
        }
    }

/*
* From Activity the EntryPoint Layout
* */
@Composable
fun ProfilesApp(profileViewModel: ProfileViewModel = viewModel()) {
    Scaffold(
    ) {
        Surface(
            modifier = Modifier.fillMaxSize().padding(it)
        ) {
            EntryPoint(profileUiState = profileViewModel.profileUiState, retryAction = profileViewModel::getProfileData)
        }
    }
}

