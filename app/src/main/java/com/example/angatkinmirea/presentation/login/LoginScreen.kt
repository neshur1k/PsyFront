package com.example.angatkinmirea.presentation.login

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(
    viewModel: LoginViewModel
) {

    var login by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    val token by viewModel.token.collectAsState()

    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        OutlinedTextField(
            value = login,
            onValueChange = {
                login = it
            },
            label = {
                Text("Логин")
            }
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
            },
            label = {
                Text("Пароль")
            }
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        Button(
            onClick = {
                viewModel.login(
                    login,
                    password
                )
            }
        ) {
            Text("Войти")
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        token?.let {

            Text(
                text = "Успешный вход"
            )
        }

        error?.let {

            Text(
                text = it
            )
        }
    }
}