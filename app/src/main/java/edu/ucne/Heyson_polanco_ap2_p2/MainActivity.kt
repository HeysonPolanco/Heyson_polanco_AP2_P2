package edu.ucne.Heyson_polanco_ap2_p2

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import dagger.hilt.android.AndroidEntryPoint
import edu.ucne.Heyson_polanco_ap2_p2.presentation.navigation.AppNavDisplay
import edu.ucne.Heyson_polanco_ap2_p2.presentation.navigation.Screen
import edu.ucne.Heyson_polanco_ap2_p2.ui.theme.Heyson_Polanco_AP2_P2Theme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Heyson_Polanco_AP2_P2Theme {
                val backStack = rememberNavBackStack(Screen.List)
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AppNavDisplay(
                        backStack = backStack,
                        innerPadding = innerPadding
                    )
                }
            }
        }
    }
}