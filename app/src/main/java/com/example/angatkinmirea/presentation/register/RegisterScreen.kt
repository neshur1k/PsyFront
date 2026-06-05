package com.example.angatkinmirea.presentation.register

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.angatkinmirea.data.remote.ApiService
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(
    onSuccess: () -> Unit
) {

    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var nickname by remember { mutableStateOf("") }

    var error by remember { mutableStateOf<String?>(null) }

    val scope = rememberCoroutineScope()
    val api = remember { ApiService() }

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

            Text(
                "Регистрация",
                style = MaterialTheme.typography.headlineMedium
            )

            Spacer(Modifier.height(16.dp))

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

            Spacer(Modifier.height(8.dp))

            OutlinedTextField(
                value = nickname,
                onValueChange = { nickname = it },
                label = { Text("Никнейм") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            Button(
                onClick = {
                    scope.launch {
                        try {
                            api.register(login, password, nickname)
                            onSuccess()
                        } catch (e: Exception) {
                            error = "error"
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Создать аккаунт")
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