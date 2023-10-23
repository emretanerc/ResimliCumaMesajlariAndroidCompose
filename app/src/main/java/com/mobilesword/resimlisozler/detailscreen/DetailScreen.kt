package com.mobilesword.resimlisozler.detailscreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.mobilesword.resimlisozler.sharescreen.AdmobBanner


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(viewModel: DetailViewModel, navController: NavController, categoryFileName: String,title:String) {
    val images by viewModel.images.observeAsState(emptyList())
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.fetchImages(categoryFileName)
    }

    viewModel.loadInterstitial(context)

    if (images.isEmpty()) {
        Text(text = "Yükleniyor...")
        viewModel.fetchImages(categoryFileName)
    } else {
        Column(Modifier.padding(10.dp)) {
            TopAppBar(
                title = {
                    Text(
                        textAlign = TextAlign.Center,
                        text = title.toString() + " Mesajları",
                        maxLines = 1,
                        color = Color.White
                    )
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
            )
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    items(images.chunked(2)) { // Her satırda iki resim göstermek için veriyi ikişerli gruplar halinde böl
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            for (item in it) {
                                Image(
                                    painter = rememberImagePainter(item.resimurl),
                                    contentDescription = "",
                                    modifier = Modifier
                                        .size(175.dp)
                                        .clickable {
                                            var adControl: Boolean = viewModel.controlInterstitial()
                                            if (adControl) {
                                                viewModel.showInterstitial(context, {
                                                    val image: String = item.resimurl
                                                    navController.navigate("share?image=$image?title=$title")
                                                })
                                            } else {
                                                val image: String = item.resimurl
                                                navController.navigate("share?image=$image?title=$title")
                                            }
                                        }
                                )
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(start = 16.dp, end = 16.dp,top = 16.dp, bottom = 16.dp)
                        ) {
                            com.mobilesword.resimlisozler.categoryscreen.AdmobBanner(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            )

        }
    }
}