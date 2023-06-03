package com.medicify.app.ui.navigation

sealed class Screen(val route: String){
    object Login : Screen("login")
    object Home : Screen("home")
    object Camera : Screen("camera")
    object Profile : Screen("profile")
//    object AgentDetails : Screen("home/{id}") {
//        fun createRoute(id: Int) = "home/$id"
//    }
}
