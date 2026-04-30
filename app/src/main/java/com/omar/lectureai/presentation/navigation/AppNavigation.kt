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
import com.omar.lectureai.presentation.result.ResultViewModel
import org.koin.androidx.compose.koinViewModel
import com.omar.lectureai.presentation.result.TranscriptData
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
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                }
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
                onNavigateToResult = { /* removed */ }
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
                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }
            }
        }

        // ── Result ────────────────────────────────────
        // ── Result ────────────────────────────────────
        composable(Routes.RESULT) {

            val viewModel: ResultViewModel = koinViewModel()

            val result by viewModel.result.collectAsState()

            if (result == null) {

                LaunchedEffect(Unit) {
                    navController.popBackStack()
                }

                return@composable
            }

            ResultScreen(

                transcriptData = TranscriptData(
                    fullText = result?.fullText ?: "",
                    blocks = result?.blocks ?: emptyList()
                ),

                onBack = {

                    pendingUri = null

                    viewModel.clearResult()

                    navController.navigate(Routes.HOME) {

                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                    }
                }
            )
        }
    }
}
