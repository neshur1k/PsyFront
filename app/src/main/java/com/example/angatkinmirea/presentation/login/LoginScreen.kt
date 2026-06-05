package com.example.angatkinmirea.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel,
    onSuccess: () -> Unit,
    onRegisterClick: () -> Unit
) {

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val token by viewModel.token.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(token) {
        if (token != null) {
            onSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {

            OutlinedTextField(
                value = login,
                onValueChange = { login = it },
                label = { Text("Логин") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Пароль") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    viewModel.login(login, password)
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Войти")
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = onRegisterClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Зарегистрироваться")
            }

            error?.let {
                Spacer(Modifier.height(12.dp))
                Text(
                    text = "Ошибка",
                    color = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}