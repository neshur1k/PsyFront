package com.example.angatkinmirea.presentation.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.angatkinmirea.data.datastore.TokenStorage
import com.example.angatkinmirea.data.remote.ApiService
import com.example.angatkinmirea.domain.model.User
import com.example.angatkinmirea.presentation.login.LoginViewModel
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(
    navController: NavHostController,
    onLogout: () -> Unit,
    loginViewModel: LoginViewModel = viewModel()
) {

    val context = LocalContext.current

    val tokenStorage = remember {
        TokenStorage(context)
    }

    var user by remember {
        mutableStateOf<User?>(null)
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }

    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        try {
            val token = tokenStorage.getToken()

            if (token != null) {
                user = ApiService().getMe(token)
            }
        } catch (e: Exception) {
            error = e.message
        }
    }

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

        user?.let {

            Text(
                text = "Логин: ${it.login}"
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Никнейм: ${it.nickname}"
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Роль: ${it.role}"
            )
        }

        error?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error
            )
        }

        Spacer(Modifier.height(24.dp))

        Button(
            onClick = {
                loginViewModel.logout {
                    onLogout()
                }
            }
        ) {
            Text("Выйти из аккаунта")
        }
    }
}