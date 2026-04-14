package com.omar.lectureai.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omar.lectureai.presentation.home.HomeScreen
import com.omar.lectureai.presentation.result.ResultScreen

private object Routes {
    const val Home = "home"
    const val Result = "result/{audioPath}"

    fun resultPath(audioPath: String): String = "result/${Uri.encode(audioPath)}"
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
                onNavigateToResult = { audioPath ->
                    navController.navigate(Routes.resultPath(audioPath))
                },
            )
        }
        composable(
            route = Routes.Result,
            arguments = listOf(navArgument("audioPath") { type = NavType.StringType }),
        ) { backStackEntry ->
            val encodedAudioPath = backStackEntry.arguments?.getString("audioPath").orEmpty()
            val audioPath = Uri.decode(encodedAudioPath)
            ResultScreen(
                audioPath = audioPath,
                onBack = { navController.popBackStack() },
            )
        }
    }
}
