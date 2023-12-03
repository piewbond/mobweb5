package hu.bme.aut.android.jot.navigation

sealed class Screen(val route: String) {
    object ExcerciseList : Screen("excercise_list")
    object ExcerciseDetail : Screen("excercise_detail/{id}"){
        fun passId(id: Int) = "excercise_detail/$id"
    }
    object ExcerciseCreate : Screen("excercise_create")
}