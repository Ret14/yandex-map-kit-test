package com.example.yandexmaptest

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.yandexmaptest.ui.theme.YandexMapTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            YandexMapTestTheme {
                // A surface container using the 'background' color from the theme
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

    Column(modifier = Modifier.fillMaxSize(),
    ) {
        Button(onClick = {
            mapLauncher.launch(mapIntent)
        }) {
            Text(text = "Click to open map")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    YandexMapTestTheme {
    }
}