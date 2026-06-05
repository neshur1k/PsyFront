package com.example.angatkinmirea.presentation.meditation

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@Composable
fun MeditationScreen() {

    var isRunning by remember {
        mutableStateOf(false)
    }

    var phase by remember {
        mutableStateOf("Готовы?")
    }

    var secondsLeft by remember {
        mutableStateOf(4)
    }

    LaunchedEffect(isRunning) {

        if (!isRunning) return@LaunchedEffect

        while (isRunning) {

            phase = "Вдох"

            for (i in 4 downTo 1) {
                secondsLeft = i
                delay(1000)
            }

            phase = "Задержка"

            for (i in 4 downTo 1) {
                secondsLeft = i
                delay(1000)
            }

            phase = "Выдох"

            for (i in 4 downTo 1) {
                secondsLeft = i
                delay(1000)
            }

            phase = "Задержка"

            for (i in 4 downTo 1) {
                secondsLeft = i
                delay(1000)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = phase,
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = secondsLeft.toString(),
            style = MaterialTheme.typography.displayLarge
        )

        Spacer(
            modifier = Modifier.height(32.dp)
        )

        Button(
            onClick = {
                isRunning = !isRunning
            }
        ) {
            Text(
                if (isRunning)
                    "Остановить"
                else
                    "Начать"
            )
        }
    }
}