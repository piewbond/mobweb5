package hu.bme.aut.android.jot

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import hu.bme.aut.android.jot.navigation.NavGraph
import hu.bme.aut.android.jot.ui.theme.JotTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val prefs = PreferenceManager.getDefaultSharedPreferences(LocalContext.current)
            var darkThemeRemember by remember { mutableStateOf(prefs.getBoolean("darkmodeOn",false)) }
            JotTheme(darkTheme = darkThemeRemember) {
                NavGraph { darkThemeRemember = !darkThemeRemember }
            }
        }
    }
}