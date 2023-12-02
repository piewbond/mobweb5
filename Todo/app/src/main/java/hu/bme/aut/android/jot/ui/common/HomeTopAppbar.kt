package hu.bme.aut.android.jot.ui.common

import android.preference.PreferenceManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppbar(

    modifier: Modifier = Modifier,
    title: String,
    actions: @Composable() (RowScope.() -> Unit) = {},
    onThemeUpdated: () -> Unit,
    onSetNotification: () -> Unit
) {
    // fetching local context
    val mContext = LocalContext.current

    val prefs = PreferenceManager.getDefaultSharedPreferences(mContext)


    // Create a boolean variable
    // to store the display menu state
    var mDisplayMenu by remember { mutableStateOf(false) }

    if (!prefs.contains("notificationsOn")){
        prefs.edit().putBoolean("notificationsOn", false).commit()
    }
    var notificationsOn by remember { mutableStateOf(prefs.getBoolean("notificationsOn",false))}

    if (!prefs.contains("darkmodeOn")){
        prefs.edit().putBoolean("darkmodeOn", false).commit()
    }
    var darkmodeOn  by remember { mutableStateOf(prefs.getBoolean("darkmodeOn",false))}


    TopAppBar(
        modifier = modifier,
        title = { Text(text = title) },
        navigationIcon = {
//            IconButton(onClick = onNavigateBack) {
//                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
//
//            }
        },
        actions = {

            // Creating Icon button for dropdown menu
            IconButton(onClick = { mDisplayMenu = !mDisplayMenu }) {
                Icon(Icons.Default.MoreVert, "")
            }

            // Creating a dropdown menu
            DropdownMenu(
                expanded = mDisplayMenu,
                onDismissRequest = { mDisplayMenu = false }
            ) {
                // Creating dropdown menu item, on click
                // would create a Toast message
                Row {
                    Spacer(modifier = Modifier.size(15.dp))
                    Text(text = "Dark mode", modifier = Modifier
                        .align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.size(35.dp))

                    Box(
                        contentAlignment = Alignment.Center
                    ) {Switch(
                        checked = darkmodeOn,
                        onCheckedChange = {
                            darkmodeOn = it
                            prefs.edit().putBoolean("darkmodeOn", darkmodeOn).apply()
                            onThemeUpdated.invoke()
                        }

                    )}
                    Spacer(modifier = Modifier.size(10.dp))
                }

                Spacer(modifier = Modifier.size(25.dp))

                Row {
                    Spacer(modifier = Modifier.size(15.dp))
                    Text(text = "Notifications", modifier = Modifier
                        .align(Alignment.CenterVertically))
                    Spacer(modifier = Modifier.size(20.dp))
                    Switch(
                        checked = notificationsOn,
                        onCheckedChange = {
                            notificationsOn = it
                            prefs.edit().putBoolean("notificationsOn", notificationsOn).apply()
                        }
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                }

                Spacer(modifier = Modifier.size(25.dp))

                Row {
                    Spacer(modifier = Modifier.size(5.dp))
                    TextButton(onClick = {
                        mDisplayMenu = false
                        onSetNotification.invoke()
                    })
                    {
                        Text(text = "Set notification time", fontSize =  16.sp, modifier = Modifier
                            .align(Alignment.CenterVertically))
                    }
                    Spacer(modifier = Modifier.size(20.dp))
                }

            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Composable
@Preview
fun HomeTopAppbar_Preview() {
    HomeTopAppbar(
        title = "Title",
        actions = {},
        onSetNotification = {},
        onThemeUpdated = {}
    )
}