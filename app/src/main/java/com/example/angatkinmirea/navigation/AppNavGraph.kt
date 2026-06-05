package com.example.angatkinmirea.navigation

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.angatkinmirea.data.datastore.TokenStorage
import com.example.angatkinmirea.presentation.createarticle.*
import com.example.angatkinmirea.presentation.feed.FeedScreen
import com.example.angatkinmirea.presentation.feed.FeedViewModel
import com.example.angatkinmirea.presentation.meditation.MeditationScreen
import com.example.angatkinmirea.presentation.profile.ProfileScreen
import com.example.angatkinmirea.presentation.login.*
import androidx.compose.foundation.layout.Box

@Composable
fun AppNavGraph(application: Application) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val tokenStorage = remember { TokenStorage(context) }

    var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }

    // проверка токена при старте
    LaunchedEffect(Unit) {
        isLoggedIn = tokenStorage.getToken() != null
    }

    // пока не знаем статус — можно показать загрузку
    if (isLoggedIn == null) {
        Box {}
        return
    }

    if (isLoggedIn == false) {

        // 🔐 LOGIN FLOW
        LoginScreen(
            viewModel = LoginViewModelFactory(application)
                .create(LoginViewModel::class.java),
            onSuccess = {
                isLoggedIn = true
            }
        )

        return
    }

    // 📱 MAIN APP FLOW
    MainScreen(navController = navController) {

        NavHost(
            navController = navController,
            startDestination = Routes.FEED
        ) {

            composable(Routes.FEED) {
                val vm: FeedViewModel = viewModel()
                FeedScreen(vm)
            }

            composable(Routes.CREATE_ARTICLE) {
                val vm = CreateArticleViewModelFactory(application)
                    .create(CreateArticleViewModel::class.java)

                CreateArticleScreen(vm)
            }

            composable(Routes.MEDITATION) {
                MeditationScreen()
            }

            composable(Routes.PROFILE) {
                ProfileScreen(
                    navController = navController,
                    onLogout = {
                        isLoggedIn = false
                    }
                )
            }
        }
    }
}