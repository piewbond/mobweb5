package hu.bme.aut.android.jot.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import hu.bme.aut.android.jot.feature.excercise_create.ExcerciseCreateScreen
import hu.bme.aut.android.jot.feature.excercise_detail.ExcerciseDetailScreen
import hu.bme.aut.android.jot.feature.excercise_list.ExcerciseListScreen

@Composable
fun NavGraph(
    navController: NavHostController = rememberNavController(),
    onThemeUpdated: () -> Unit,

    ) {
    NavHost(
        navController = navController,
        startDestination = Screen.ExcerciseList.route
    ) {
        composable(Screen.ExcerciseList.route) {
            ExcerciseListScreen(
                onListItemClick = {
                    navController.navigate(Screen.ExcerciseDetail.passId(it))
                },
                onFabClick = {
                    navController.navigate(Screen.ExcerciseCreate.route)
                },
                onThemeUpdated = onThemeUpdated
            )
        }
        composable(
            route = Screen.ExcerciseDetail.route,
            arguments = listOf(
                navArgument("id") {
                    type = NavType.IntType
                }
            )
        ) {
            ExcerciseDetailScreen(onNavigateBack = { navController.popBackStack() })
        }
        composable(Screen.ExcerciseCreate.route) {
            ExcerciseCreateScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}