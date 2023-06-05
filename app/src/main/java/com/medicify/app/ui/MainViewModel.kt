package com.medicify.app.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.medicify.app.ui.navigation.Screen
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    var user: MutableState<FirebaseUser?> = mutableStateOf(Firebase.auth.currentUser)

    var isAuthenticated: MutableState<Boolean> = mutableStateOf(user.value != null)

    fun onSignOutClick(restartApp: (String) -> Unit) {
        viewModelScope.launch {
            Firebase.auth.signOut()
            user.value = null
            isAuthenticated.value = false
            restartApp(Screen.Login.route)
        }
    }
}