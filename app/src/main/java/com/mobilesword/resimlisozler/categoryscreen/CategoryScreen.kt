package com.mobilesword.resimlisozler.categoryscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.mobilesword.resimlisozler.Screen


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoryScreen(viewModel: CategoryViewModel,navController: NavController) {
    val categories by viewModel.categories.observeAsState(emptyList())

    LaunchedEffect(Unit) {
        viewModel.fetchCategories()
    }


    Column(Modifier.padding(10.dp)) {
        if (categories.isEmpty()) {
            Text(text = "Yükleniyor...")
            viewModel.fetchCategories()
        } else {
            TopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = "Kategoriler",
                        maxLines = 1,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp,top = 16.dp)
            ) {
                AdmobBanner(modifier = Modifier.fillMaxWidth())
            }
            LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp), contentPadding = PaddingValues(10.dp)) {
                items(categories.size) {
                    Text( fontSize = 20.sp, text =  categories.get(it).kategoriadi,
                        modifier = Modifier
                            .clickable {
                                val data : String = categories.get(it).dosyaadi
                                val title : String = categories.get(it).kategoriadi
                                navController.navigate("detail?data=$data?title=$title")
                            })
                }
            }
            Box(
                modifier = Modifier
                    .padding(start = 16.dp, end = 16.dp)
            ) {
                AdmobBanner(modifier = Modifier.fillMaxWidth())
            }

        }

    }
}

@Composable
fun AdmobBanner(modifier: Modifier = Modifier) {
    AndroidView(
        modifier = Modifier.fillMaxWidth(),
        factory = { context ->
            AdView(context).apply {
                setAdSize(AdSize.BANNER)
                adUnitId = "ca-app-pub-3940256099942544/9214589741"
                loadAd(AdRequest.Builder().build())
            }
        }
    )
}

