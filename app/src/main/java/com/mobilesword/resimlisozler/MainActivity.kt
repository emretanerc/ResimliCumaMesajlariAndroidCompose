package com.mobilesword.resimlisozler

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import com.mobilesword.resimlisozler.categoryscreen.CategoryScreen
import com.mobilesword.resimlisozler.categoryscreen.CategoryViewModel
import com.mobilesword.resimlisozler.ui.theme.ResimliCumaMesajlarıTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MobileAds.initialize(this) {}
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder().setTestDeviceIds(listOf("BC7173F5C494BDD559A3DEDAD8E919FD")).build()
        )

        setContent {
            ResimliCumaMesajlarıTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    MyNavGraph(navController = navController)
                }
            }
        }
    }
}
