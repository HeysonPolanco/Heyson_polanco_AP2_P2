package edu.ucne.Heyson_polanco_ap2_p2.presentation.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import edu.ucne.Heyson_polanco_ap2_p2.presentation.detail.DetailScreen
import edu.ucne.Heyson_polanco_ap2_p2.presentation.list.ListScreen

@Composable
fun AppNavDisplay(
    backStack: NavBackStack<NavKey>,
    innerPadding: PaddingValues
){
    NavDisplay(
        backStack = backStack,
        modifier = Modifier.padding(innerPadding),
        entryProvider = entryProvider {
            entry<Screen.List>{
                ListScreen(
                    onNavigateToCreate = {
                        backStack.add(Screen.Detail(id = 0))
                    },
                    onNavigateToEdit = { id ->
                        backStack.add(Screen.Detail(id = id))
                    }
                )
            }

            entry<Screen.Detail>{ key ->
                DetailScreen(
                    id = key.id,
                    onBack = {
                        if(backStack.isNotEmpty()) backStack.removeAt(backStack.size - 1)
                    }
                )
            }
        }
    )
}