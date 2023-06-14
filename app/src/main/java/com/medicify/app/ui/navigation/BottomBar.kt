package com.medicify.app.ui.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.medicify.app.R
import com.medicify.app.ui.utils.getVectorResource
import com.medicify.app.R.drawable as ApplicationIcon

@Composable
fun BottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val navigationItems = listOf(
        NavigationItem(
            title = stringResource(R.string.navigation_item_home),
            icon = ApplicationIcon.home_icon_24px.getVectorResource(),
            screen = Screen.Home
        ),

        NavigationItem(
            title = stringResource(R.string.navigation_item_camera),
            icon = ApplicationIcon.camera_icon_24px.getVectorResource(),
            screen = Screen.Camera
        ),

        NavigationItem(
            title = stringResource(R.string.navigation_item_profile),
            icon = ApplicationIcon.profile_icon_24px.getVectorResource(),
            screen = Screen.Profile
        ),
    )
    NavigationBar(modifier = modifier) {
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                label = { Text(item.title) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
            )
        }
    }

}