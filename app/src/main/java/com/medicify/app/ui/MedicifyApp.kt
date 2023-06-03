package com.medicify.app.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.medicify.app.R
import com.medicify.app.data.firebase.rememberFirebaseAuthLauncher
import com.medicify.app.ui.navigation.BottomBar
import com.medicify.app.ui.navigation.Screen
import com.medicify.app.ui.screen.home.HomeScreen
import com.medicify.app.ui.screen.login.LoginScreen
import com.medicify.app.ui.theme.MedicifyTheme
import com.medicify.app.ui.screen.camera.CameraScreen
import com.medicify.app.ui.screen.profile.ProfileScreen

@Composable
fun MedicifyApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    var user by remember { mutableStateOf(Firebase.auth.currentUser) }
    var isAuthenticated by remember {
        mutableStateOf(user != null)
    }
    val launcher = rememberFirebaseAuthLauncher(
        onAuthComplete = { result ->
            user = result.user
            isAuthenticated = true
            navController.popBackStack()
            navController.navigate(Screen.Home.route) {
                popUpTo(navController.graph.findStartDestination().id) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        onAuthError = {
            isAuthenticated = false
            user = null
        }
    )
    val token = stringResource(R.string.default_web_client_id)
    val context = LocalContext.current

    val initialRoute = if (isAuthenticated) Screen.Home.route else Screen.Login.route

    Scaffold(
        modifier = modifier,
        bottomBar = {
            val routeWithBottomNavigation = listOf(Screen.Home.route, Screen.Profile.route)
            if (currentRoute in routeWithBottomNavigation) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = initialRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Login.route) {
                LoginScreen(
                    onSignInCLick = {
                        val gso =
                            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestIdToken(token)
                                .requestEmail()
                                .build()
                        val googleSignInClient = GoogleSignIn.getClient(context, gso)
                        launcher.launch(googleSignInClient.signInIntent)
                    },
                    toCamera = {

                    },
                )
            }
            composable(Screen.Home.route) {
                HomeScreen(
                    user = user,
                    navigateToDetail = {
                        Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    }
                )
            }
            composable(Screen.Camera.route) {
                CameraScreen()
            }
            composable(Screen.Profile.route){
                ProfileScreen()
            }

        }
    }


}

@Preview
@Composable
fun MedicifyAppPreview() {
    MedicifyTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            MedicifyApp()
        }
    }
}