package com.example.angatkinmirea.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.foundation.layout.Box

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
                    Text("Psychology App")
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

                listOf(
                    BottomNavItem(Routes.FEED, "Статьи"),
                    BottomNavItem(Routes.CREATE_ARTICLE, "Создать"),
                    BottomNavItem(Routes.MEDITATION, "Медитация")
                ).forEach { item ->

                    NavigationBarItem(
                        selected = false,
                        onClick = {
                            navController.navigate(item.route)
                        },
                        icon = {},
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