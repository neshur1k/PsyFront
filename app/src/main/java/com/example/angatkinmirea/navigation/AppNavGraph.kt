package com.example.angatkinmirea.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.angatkinmirea.presentation.createarticle.CreateArticleScreen
import com.example.angatkinmirea.presentation.feed.FeedScreen
import com.example.angatkinmirea.presentation.meditation.MeditationScreen
import com.example.angatkinmirea.presentation.profile.ProfileScreen

@Composable
fun AppNavGraph() {

    val navController =
        rememberNavController()

    MainScreen(
        navController = navController
    ) {

        NavHost(
            navController = navController,
            startDestination = Routes.FEED
        ) {

            composable(
                Routes.FEED
            ) {
                FeedScreen()
            }

            composable(
                Routes.CREATE_ARTICLE
            ) {
                CreateArticleScreen()
            }

            composable(
                Routes.MEDITATION
            ) {
                MeditationScreen()
            }

            composable(
                Routes.PROFILE
            ) {
                ProfileScreen()
            }
        }
    }
}