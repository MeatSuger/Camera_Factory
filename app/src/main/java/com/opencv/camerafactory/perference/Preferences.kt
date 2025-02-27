package com.opencv.camerafactory.perference

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alorma.compose.settings.ui.SettingsCheckbox
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import com.alorma.compose.settings.ui.SettingsTriStateCheckbox
import com.alorma.compose.settings.ui.base.internal.LocalSettingsTileColors
import com.alorma.compose.settings.ui.base.internal.SettingsTileDefaults
import com.opencv.camerafactory.ui.theme.CameraFactoryTheme


@Preview(
    showSystemUi = true, device = "spec:width=1080px,height=2340px,dpi=440",
    showBackground = true,
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Composable
fun Preferences() {
    CameraFactoryTheme {
        Scaffold(
            modifier = Modifier,
            containerColor = MaterialTheme.colorScheme.surfaceContainer
        ) { padding ->
            val scrollState = rememberScrollState()
            Column(
                modifier = Modifier
                    .consumeWindowInsets(padding)
                    .verticalScroll(scrollState)
                    .padding(top = padding.calculateTopPadding())
            ) {
                SettingsGroup(
                    modifier = Modifier,
                    enabled = true,
                    title = { Text(text = "SettingsGroup") },
                    contentPadding = PaddingValues(16.dp),
                ) {
                    ElevatedCard(
                        colors =
                        CardDefaults.elevatedCardColors(
                            containerColor = (LocalSettingsTileColors.current
                                ?: SettingsTileDefaults.colors()
                                    ).containerColor,
                        ),
                    )
                    {
                        SettingsTriStateCheckbox(
                            state = false,
                            title = { Text(text = "this is a title") },
                            subtitle = { Text(text = "this is 二级描述") },
                            enabled = true,
                        )
                        SettingsMenuLink(
                            title = { Text(text = "Menu link tile") },
                            onClick = {},
                        )
                        SettingsCheckbox(
                            state = true,
                            title = { Text(text = "Checkbox tile") },
                            onCheckedChange = {},
                        )
                        SettingsSwitch(
                            state = true,
                            title = { Text(text = "Switch tile") },
                            onCheckedChange = {},
                        )
                    }
                }
            }
        }
    }
}