package com.omar.lectureai.presentation.navigation

import android.net.Uri
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.omar.lectureai.presentation.auth.LoginScreen
import com.omar.lectureai.presentation.home.HomeScreen
import com.omar.lectureai.presentation.processing.ProcessingScreen
import com.omar.lectureai.presentation.result.ResultScreen

private object Routes {
    const val LOGIN      = "login"
    const val HOME       = "home"
    const val PROCESSING = "processing"
    const val RESULT     = "result"
}

@Composable
fun AppNavigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    var pendingUri by remember { mutableStateOf<Uri?>(null) }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN,
        modifier = modifier
    ) {

        // ── Login ─────────────────────────────────────
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) { inclusive = true }
                    }
                },
                onCreateAccount = { /* TODO: navigate to Register */ }
            )
        }

        // ── Home ──────────────────────────────────────
        composable(Routes.HOME) {
            HomeScreen(
                onUploadClick = { uri ->
                    pendingUri = uri
                    navController.navigate(Routes.PROCESSING)
                },
                onRecordClick = { uri ->
                    pendingUri = uri
                    navController.navigate(Routes.PROCESSING)
                },
                onHistoryClick = { /* TODO */ },
                onNavigateToResult = { /* ❌ removed direct navigation */ }
            )
        }

        // ── Processing ────────────────────────────────
        composable(Routes.PROCESSING) {
            val uri = pendingUri

            if (uri != null) {
                ProcessingScreen(
                    audioUri = uri,
                    onFinished = {
                        navController.navigate(Routes.RESULT) {
                            popUpTo(Routes.PROCESSING) { inclusive = true }
                        }
                    }
                )
            } else {
                // 🔥 FIX: prevent blank screen
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
        }

        // ── Result ────────────────────────────────────
        composable(Routes.RESULT) {
            ResultScreen(
                onBack = {
                    pendingUri = null
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) { inclusive = true }
                    }
                }
            )
        }
    }
}