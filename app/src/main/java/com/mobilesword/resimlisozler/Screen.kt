package com.mobilesword.resimlisozler

sealed class Screen(val route: String) {
    object MainScreen: Screen("main")
    object DetailScreen: Screen("detail?data={data}?title={title}")
    object ShareScreen: Screen("share?image={image}?title={title}")
}