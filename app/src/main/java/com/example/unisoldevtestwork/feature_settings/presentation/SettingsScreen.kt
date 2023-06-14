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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.unisoldevtestwork.R


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    settingsState: SettingsState,
    onNavigateBack: () -> Unit,
    onUpdateTheme: (mode: ThemeOption) -> Unit,
    onUpdateQualityOfImages: (quality: QualityOption) -> Unit,
    onUpdateNetworkType: (networkType: NetworkType) -> Unit
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
            ThemeItemChoice(settingsState.themeMode, onUpdateTheme)
            QualityOfImageChoice(settingsState.qualityOfImage, onUpdateQualityOfImages)
            NetWorkTypeChoice(settingsState.networkType, onUpdateNetworkType)
        }
    }
}


@Composable
fun ThemeItemChoice(themeMode: ThemeOption, onUpdateTheme: (mode: ThemeOption) -> Unit) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val radioOptions = listOf(
        Pair(ThemeOption.LIGHT_THEME, R.string.light_theme),
        Pair(ThemeOption.DARK_THEME, R.string.dark_theme),
        Pair(ThemeOption.SYSTEM_THEME, R.string.apply_theme_system)
    )
    val selectedOption = remember { mutableStateOf(themeMode) }

    ListItem(
        modifier = Modifier.clickable { openAlertDialog.value = true },
        headlineContent = { Text(text = stringResource(id = R.string.theme_of_application)) },
        supportingContent = {
            Text(text = stringResource(id = radioOptions.find {
                it.first == themeMode
            }?.second ?: R.string.theme_of_application))
        }
    )

    if (openAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { openAlertDialog.value = false },
            title = { Text(text = stringResource(id = R.string.theme_of_application)) },
            text = {
                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { (option, textRes) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = option == selectedOption.value,
                                    onClick = { selectedOption.value = option },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = option == selectedOption.value, onClick = null)
                            Text(
                                text = stringResource(id = textRes),
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
                    onUpdateTheme(selectedOption.value)
                }) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { openAlertDialog.value = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }
}


@Composable
fun QualityOfImageChoice(
    qualityOfImage: QualityOption,
    onUpdateQualityOfImages: (quality: QualityOption) -> Unit
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val radioOptions = listOf(
        Pair(QualityOption.WITHOUT_COMPRESSION, R.string.high_quality_of_image),
        Pair(QualityOption.WITH_COMPRESSION_75, R.string.small_compression),
        Pair(QualityOption.SMALL_IMAGE_WITH_COMPRESSION_75, R.string.high_compression)
    )
    val selectedOption = remember { mutableStateOf(qualityOfImage) }

    ListItem(
        modifier = Modifier.clickable { openAlertDialog.value = true },
        headlineContent = { Text(text = stringResource(id = R.string.quality_of_images)) },
        supportingContent = {
            Text(
                text = stringResource(
                    id = radioOptions.find { it.first == qualityOfImage }?.second
                        ?: R.string.quality_of_images
                )
            )
        }
    )

    if (openAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { openAlertDialog.value = false },
            title = { Text(text = stringResource(id = R.string.quality_of_images)) },
            text = {
                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { (option, textRes) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = option == selectedOption.value,
                                    onClick = { selectedOption.value = option },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = option == selectedOption.value, onClick = null)
                            Text(
                                text = stringResource(id = textRes),
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
                    onUpdateQualityOfImages(selectedOption.value)
                }) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { openAlertDialog.value = false }) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }
}


@Composable
fun NetWorkTypeChoice(
    networkType: NetworkType,
    onUpdateNetworkType: (networkType: NetworkType) -> Unit
) {
    val openAlertDialog = remember { mutableStateOf(false) }
    val radioOptions = listOf(
        Pair(NetworkType.DEFAULT, R.string.download_both),
        Pair(NetworkType.WIFI, R.string.download_with_wifi),
        Pair(NetworkType.MOBILE_DATA, R.string.download_with_mobile_internet)
    )
    val selectedOption = remember { mutableStateOf(networkType) }

    ListItem(
        modifier = Modifier.clickable { openAlertDialog.value = true },
        headlineContent = { Text(text = stringResource(id = R.string.download_image_using)) },
        supportingContent = {
            Text(
                text = stringResource(
                    id = radioOptions.find { it.first == networkType }?.second ?: 0
                )
            )
        }
    )

    if (openAlertDialog.value) {
        AlertDialog(
            onDismissRequest = { openAlertDialog.value = false },
            title = { Text(text = stringResource(id = R.string.download_image_using)) },
            text = {
                Column(Modifier.selectableGroup()) {
                    radioOptions.forEach { (option, textRes) ->
                        Row(
                            Modifier
                                .fillMaxWidth()
                                .selectable(
                                    selected = option == selectedOption.value,
                                    onClick = { selectedOption.value = option },
                                    role = Role.RadioButton
                                )
                                .padding(vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            RadioButton(selected = option == selectedOption.value, onClick = null)
                            Text(
                                text = stringResource(id = textRes),
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
                    onUpdateNetworkType(selectedOption.value)
                }) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(onClick = { openAlertDialog.value = false }) {
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
        settingsState = SettingsState(
            ThemeOption.DARK_THEME,
            QualityOption.WITHOUT_COMPRESSION,
            NetworkType.DEFAULT
        ),
        onNavigateBack = {},
        onUpdateTheme = {},
        onUpdateQualityOfImages = {

        },
        onUpdateNetworkType = {

        }
    )
}