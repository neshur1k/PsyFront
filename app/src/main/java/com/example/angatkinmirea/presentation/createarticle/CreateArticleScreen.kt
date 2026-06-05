package com.example.angatkinmirea.presentation.createarticle

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateArticleScreen(
    viewModel: CreateArticleViewModel
) {

    var title by remember {
        mutableStateOf("")
    }

    var content by remember {
        mutableStateOf("")
    }

    var category by remember {
        mutableStateOf("")
    }

    var expanded by remember {
        mutableStateOf(false)
    }

    val categories = mapOf(
        "Тревога" to "ANXIETY",
        "Стресс" to "STRESS",
        "Медитация" to "MEDITATION",
        "Мотивация" to "MOTIVATION",
        "Самооценка" to "SELF_ESTEEM"
    )

    var selectedCategoryName by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),

        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Создание статьи",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
            },
            label = {
                Text("Заголовок")
            },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {

            OutlinedTextField(
                value = selectedCategoryName,
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Категория")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = {
                    expanded = false
                }
            ) {

                categories.forEach { (displayName, enumValue) ->

                    DropdownMenuItem(
                        text = {
                            Text(displayName)
                        },
                        onClick = {
                            selectedCategoryName = displayName
                            category = enumValue
                            expanded = false
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        OutlinedTextField(
            value = content,
            onValueChange = {
                content = it
            },
            label = {
                Text("Текст статьи")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(180.dp),
            minLines = 6
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Button(
            onClick = {
                viewModel.createArticle(
                    title,
                    content,
                    category
                ) {
                    title = ""
                    content = ""
                    category = ""
                    selectedCategoryName = ""
                }
            }
        ) {
            Text("Опубликовать")
        }
    }
}