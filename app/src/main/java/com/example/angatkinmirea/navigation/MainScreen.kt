package com.example.angatkinmirea.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun MainScreen(
    navController: NavHostController,
    content: @Composable () -> Unit
) {

    Scaffold(

        bottomBar = {

            NavigationBar {

                listOf(

                    BottomNavItem(
                        Routes.FEED,
                        "Статьи"
                    ),

                    BottomNavItem(
                        Routes.CREATE_ARTICLE,
                        "Создать"
                    ),

                    BottomNavItem(
                        Routes.MEDITATION,
                        "Медитация"
                    )

                ).forEach { item ->

                    NavigationBarItem(

                        selected = false,

                        onClick = {

                            navController.navigate(
                                item.route
                            )
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

        androidx.compose.foundation.layout.Box(
            modifier =
            androidx.compose.ui.Modifier
                .padding(padding)
        ) {

            content()
        }
    }
}