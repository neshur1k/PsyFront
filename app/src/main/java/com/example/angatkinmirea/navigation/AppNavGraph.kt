package com.example.angatkinmirea.navigation

import android.app.Application
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.*
import com.example.angatkinmirea.data.datastore.TokenStorage
import com.example.angatkinmirea.presentation.createarticle.*
import com.example.angatkinmirea.presentation.feed.*
import com.example.angatkinmirea.presentation.meditation.MeditationScreen
import com.example.angatkinmirea.presentation.profile.ProfileScreen
import com.example.angatkinmirea.presentation.login.*
import com.example.angatkinmirea.presentation.register.RegisterScreen
import androidx.compose.foundation.layout.Box

@Composable
fun AppNavGraph(application: Application) {

    val navController = rememberNavController()
    val context = LocalContext.current
    val tokenStorage = remember { TokenStorage(context) }

    var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        isLoggedIn = tokenStorage.getToken() != null
    }

    if (isLoggedIn == null) {
        Box {}
        return
    }

    // 🔐 AUTH FLOW
    if (isLoggedIn == false) {

        NavHost(
            navController = navController,
            startDestination = Routes.LOGIN
        ) {

            composable(Routes.LOGIN) {
                LoginScreen(
                    viewModel = LoginViewModelFactory(application)
                        .create(LoginViewModel::class.java),

                    onSuccess = {
                        isLoggedIn = true
                    },

                    onRegisterClick = {
                        navController.navigate(Routes.REGISTER)
                    }
                )
            }

            composable(Routes.REGISTER) {
                RegisterScreen(
                    onSuccess = {
                        navController.popBackStack()
                    }
                )
            }
        }

        return
    }

    // 🟢 MAIN APP FLOW (WITH BOTTOM BAR)
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