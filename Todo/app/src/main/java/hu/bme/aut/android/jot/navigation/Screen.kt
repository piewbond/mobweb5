package hu.bme.aut.android.jot.navigation

sealed class Screen(val route: String) {
    object TodoList : Screen("excercise_list")
    object TodoDetail : Screen("excercise_detail/{id}"){
        fun passId(id: Int) = "excercise_detail/$id"
    }
    object TodoCreate : Screen("excercise_create")
}