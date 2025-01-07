package com.varun.myapplication

//import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.location.LocationServices
import com.mapbox.android.core.permissions.PermissionsManager
import com.varun.myapplication.ui.theme.MyApplicationTheme
import com.mapbox.geojson.Point
import com.mapbox.maps.extension.compose.MapEffect
import com.mapbox.maps.extension.compose.MapboxMap
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.PuckBearing
import com.mapbox.maps.plugin.locationcomponent.createDefault2DPuck
import com.mapbox.maps.plugin.locationcomponent.location
import kotlinx.coroutines.launch

class MainActivity6 : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val intent = intent
            val totalCost = intent.getDoubleExtra("money",0.00)


//                val mapViewportState = rememberMapViewportState()
//                MapboxMap(
//                    Modifier.fillMaxSize(),
//                    mapViewportState = mapViewportState,
//                ) {
//                    MapEffect(Unit) { mapView ->
//                        mapView.location.updateSettings {
//                            locationPuck = createDefault2DPuck(withBearing = true)
//                            enabled = true
//                            puckBearing = PuckBearing.COURSE
//                            puckBearingEnabled = true
//                        }
//                        mapViewportState.transitionToFollowPuckState()
//                    }
//                }
//            }
            Scaffold(
                floatingActionButton = {
                    FloatingActionButton(onClick = {
                        val intent = Intent(this, placeorderactivity::class.java)
                        intent.putExtra("money", totalCost)
                        this.startActivity(intent)

                    }) {
                        Icon(Icons.Default.Done, contentDescription = "Back")
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                    val mapViewportState = rememberMapViewportState()
                    MapboxMap(
                        Modifier.fillMaxSize(),
                        mapViewportState = mapViewportState,
                    ) {
                        MapEffect(Unit) { mapView ->
                            mapView.location.updateSettings {
                                locationPuck = createDefault2DPuck(withBearing = true)
                                enabled = true
                                puckBearing = PuckBearing.COURSE
                                puckBearingEnabled = true
                            }
                            mapViewportState.transitionToFollowPuckState()
                        }
                    }
                }
            }
        }
    }
}

