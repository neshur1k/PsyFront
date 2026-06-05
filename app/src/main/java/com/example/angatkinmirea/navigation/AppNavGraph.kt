package com.example.angatkinmirea.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.angatkinmirea.presentation.createarticle.CreateArticleScreen
import com.example.angatkinmirea.presentation.createarticle.CreateArticleViewModelFactory
import com.example.angatkinmirea.presentation.feed.FeedScreen
import com.example.angatkinmirea.presentation.feed.FeedViewModel
import com.example.angatkinmirea.presentation.meditation.MeditationScreen
import com.example.angatkinmirea.presentation.profile.ProfileScreen
import com.example.angatkinmirea.presentation.createarticle.CreateArticleViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import android.app.Application

@Composable
fun AppNavGraph(application: Application) {

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
                val viewModel: FeedViewModel = viewModel()

                FeedScreen(
                    viewModel = viewModel
                )
            }

            composable(
                Routes.CREATE_ARTICLE
            ) {

                val viewModel =
                    CreateArticleViewModelFactory(
                        application
                    ).create(
                        CreateArticleViewModel::class.java
                    )

                CreateArticleScreen(
                    viewModel = viewModel
                )
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