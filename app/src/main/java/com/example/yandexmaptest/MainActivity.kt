package com.example.yandexmaptest

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.yandexmaptest.ui.theme.YandexMapTestTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YandexMapTestTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Greeting()
                }
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun Greeting() {
    var latitude by remember {
        mutableStateOf(0.0)
    }
    var longitude by remember {
        mutableStateOf(0.0)
    }
    val mapLauncher = rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
        result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
            result.data?.let {
                latitude = it.getDoubleExtra(MapActivity.LATITUDE_EXTRA, 0.0)
                longitude = it.getDoubleExtra(MapActivity.LONGITUDE_EXTRA, 0.0)
                Log.d("LAT_LONG", "$latitude $longitude")
            }
        }
    }
    val mapIntent = MapActivity.newIntent(LocalContext.current, latitude, longitude)

    val multiplePermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = {
//            if (!multiplePermissionsState.allPermissionsGranted)
//                multiplePermissionsState.launchMultiplePermissionRequest()
            mapLauncher.launch(mapIntent)
        }) {
            Text(text = "Click to open map")
        }
    }
}