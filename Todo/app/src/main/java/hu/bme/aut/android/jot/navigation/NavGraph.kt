package hu.bme.aut.android.jot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.jot.feature.excercise_create.TodoCreateScreen
import hu.bme.aut.android.jot.feature.excercise_detail.TodoDetailScreen
import hu.bme.aut.android.jot.feature.excercise_list.TodoListScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    onThemeUpdated: () -> Unit,

    ) {
    NavHost(
        navController = navController,
        startDestination = Screen.TodoList.route
    ) {
        composable(Screen.TodoList.route) {
            TodoListScreen(
                onListItemClick = {
                    navController.navigate(Screen.TodoDetail.passId(it))
                },
                onFabClick = {
                    navController.navigate(Screen.TodoCreate.route)
                },
                onThemeUpdated = onThemeUpdated
            )
        }
        composable(
            route = Screen.TodoDetail.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            TodoDetailScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.TodoCreate.route) {
            TodoCreateScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}