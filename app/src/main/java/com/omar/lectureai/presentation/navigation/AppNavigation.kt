package com.omar.lectureai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omar.lectureai.presentation.home.HomeScreen
import com.omar.lectureai.presentation.result.ResultScreen

private object Routes {
    const val Home = "home"
    const val Result = "result"
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Routes.Home,
    ) {
        composable(Routes.Home) {
            HomeScreen(
                onNavigateToResult = { navController.navigate(Routes.Result) },
            )
        }
        composable(Routes.Result) {
            ResultScreen(
                onBack = { navController.popBackStack() },
            )
        }
    }
}
