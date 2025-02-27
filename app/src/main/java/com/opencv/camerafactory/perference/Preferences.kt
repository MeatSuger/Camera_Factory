package com.opencv.camerafactory.perference

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.alorma.compose.settings.ui.SettingsGroup
import com.alorma.compose.settings.ui.SettingsTriStateCheckbox
import com.opencv.camerafactory.ui.theme.CameraFactoryTheme

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showSystemUi = true)
@Composable
fun Preferences() {

//
    CameraFactoryTheme {
        Surface {
            SettingsGroup(
                modifier = Modifier,
                enabled = true,
                title = { Text("this is settings group title") },
                contentPadding = PaddingValues(16.dp)
            ) {
                SettingsTriStateCheckbox(
                    state = false,
                    title = { Text(text = "this is a title") },
                    modifier = Modifier,
                    enabled = true,
                )

            }


        }

    }
}
