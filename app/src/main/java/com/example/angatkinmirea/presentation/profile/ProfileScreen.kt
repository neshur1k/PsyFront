package com.example.angatkinmirea.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.angatkinmirea.presentation.login.LoginViewModel

@Composable
fun ProfileScreen(
    navController: NavHostController,
    onLogout: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        Text(
            text = "Профиль",
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(Modifier.height(16.dp))

        Text("Здесь будет никнейм")
        Text("Здесь будет описание")

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                // очищаем токен
                loginViewModel.logout {
                    // переключаем приложение в auth режим
                    onLogout()
                }
            }
        ) {
            Text("Выйти из аккаунта")
        }
    }
}