package com.example.unisoldevtestwork.feature_settings.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unisoldevtestwork.R
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeConstants.DARK_THEME
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeConstants.LIGHT_THEME
import com.example.unisoldevtestwork.feature_settings.presentation.ThemeConstants.SYSTEM_THEME


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onNavigateBack: () -> Unit,
    onUpdateTheme: (mode: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.settings))
                }, navigationIcon = {
                    IconButton(onClick = { onNavigateBack() }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.back_button
                            )
                        )
                    }
                }
            )
        },
    ) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            ThemeItem(settingsState.themeMode, onUpdateTheme)
        }
    }
}

@Composable
fun ThemeItem(themeMode: String, onUpdateTheme: (mode: String) -> Unit) {
    val openAlertDialog = remember { mutableStateOf(false) }
    ListItem(modifier = Modifier.clickable {
        openAlertDialog.value = true
    }, headlineContent = {
        Text(text = stringResource(id = R.string.theme_of_application))
    }, supportingContent = {
        when(themeMode) {
            SYSTEM_THEME -> {
                Text(text = stringResource(id = R.string.apply_theme_system))
            }
            DARK_THEME -> {
                Text(text = stringResource(id = R.string.dark_theme))
            }
            LIGHT_THEME -> {
                Text(text = stringResource(id = R.string.light_theme))
            }
        }
    })
    val radioOptions = listOf(
        R.string.light_theme, R.string.dark_theme, R.string.apply_theme_system
    )
    var selectedOption by remember { mutableIntStateOf(radioOptions[0]) }
    if (openAlertDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openAlertDialog.value = false
            },
            title = {
                Text(text = stringResource(id = R.string.theme_of_application))
            },
            text = {
                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { text ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = text == selectedOption,
                                    onClick = {
                                        selectedOption = text
                                    },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(
                                selected = (text == selectedOption), onClick = null
                            )
                            Text(
                                text = stringResource(id = text),
                                style = MaterialTheme.typography.bodyLarge,
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    openAlertDialog.value = false
                    when (selectedOption) {
                        R.string.light_theme -> {
                            onUpdateTheme(LIGHT_THEME)
                        }

                        R.string.dark_theme -> {
                            onUpdateTheme(DARK_THEME)
                        }

                        R.string.apply_theme_system -> {
                            onUpdateTheme(SYSTEM_THEME)
                        }
                    }
                }) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    openAlertDialog.value = false
                }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen(
        settingsState = SettingsState(""),
        onNavigateBack = {},
        onUpdateTheme = {}
    )
}