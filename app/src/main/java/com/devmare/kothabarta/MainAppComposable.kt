package com.devmare.kothabarta

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.devmare.kothabarta.feature.auth.signin.SignInScreen
import com.devmare.kothabarta.feature.auth.signup.SignUpScreen
import com.devmare.kothabarta.feature.chat.ChatScreen
import com.devmare.kothabarta.feature.home.HomeScreen
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MainApp() {
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        val navController = rememberNavController()
        val currentUser = FirebaseAuth.getInstance().currentUser

        NavHost(
            navController = navController,
            startDestination = if (currentUser == null) "login" else "home"
        ) {
            composable("login") {
                SignInScreen(navController)
            }

            composable("signup") {
                SignUpScreen(navController)
            }

            composable("home") {
                HomeScreen(navController)
            }

            composable("chat/{channelId}", arguments = listOf(
                navArgument("channelId") {
                    type = NavType.StringType
                }
            )) {
                val channelId = it.arguments?.getString("channelId") ?: ""
                ChatScreen(navController, channelId)
            }
        }
    }
}