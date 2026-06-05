package com.example.angatkinmirea.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Article
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController,
    content: @Composable () -> Unit
) {

    Scaffold(

        topBar = {
            TopAppBar(
                title = {
                    Text("Psy")
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate(Routes.PROFILE)
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Person,
                            contentDescription = "Profile"
                        )
                    }
                }
            )
        },

        bottomBar = {
            NavigationBar {

                val items = listOf(
                    BottomNavItem(
                        Routes.FEED,
                        "Статьи",
                        Icons.Default.Article
                    ),
                    BottomNavItem(
                        Routes.CREATE_ARTICLE,
                        "Создать",
                        Icons.Default.Add
                    ),
                    BottomNavItem(
                        Routes.MEDITATION,
                        "Медитация",
                        Icons.Default.SelfImprovement
                    )
                )

                items.forEach { item ->

                    NavigationBarItem(
                        selected = false,

                        onClick = {
                            navController.navigate(item.route) {
                                launchSingleTop = true
                            }
                        },

                        icon = {
                            Icon(
                                imageVector = item.icon,
                                contentDescription = item.title
                            )
                        },

                        label = {
                            Text(item.title)
                        }
                    )
                }
            }
        }

    ) { padding ->

        Box(
            modifier = Modifier.padding(padding)
        ) {
            content()
        }
    }
}