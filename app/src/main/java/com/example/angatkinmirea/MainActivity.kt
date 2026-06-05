package com.example.angatkinmirea
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.angatkinmirea.navigation.AppNavGraph
import com.example.angatkinmirea.presentation.login.LoginScreen
import com.example.angatkinmirea.presentation.login.LoginViewModel
import com.example.angatkinmirea.presentation.login.LoginViewModelFactory
import com.example.angatkinmirea.presentation.ui.theme.AngatkinMIREATheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AngatkinMIREATheme {
                /*
                val viewModel =
                    LoginViewModelFactory(application)
                        .create(
                            LoginViewModel::class.java
                        )

                LoginScreen(
                    viewModel
                )

                 */
                AppNavGraph(
                    application = application
                )
            }
        }
    }
}