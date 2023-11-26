package hu.bme.aut.android.jot

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import hu.bme.aut.android.jot.navigation.NavGraph
import hu.bme.aut.android.jot.ui.theme.JotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JotTheme {
                NavGraph()
            }
        }
    }
}