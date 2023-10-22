package com.mobilesword.resimlisozler

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.mobilesword.resimlisozler.categoryscreen.CategoryScreen
import com.mobilesword.resimlisozler.categoryscreen.CategoryViewModel
import com.mobilesword.resimlisozler.detailscreen.DetailScreen
import com.mobilesword.resimlisozler.detailscreen.DetailViewModel
import com.mobilesword.resimlisozler.sharescreen.ShareScreen
import com.mobilesword.resimlisozler.sharescreen.ShareViewModel

@Composable
fun MyNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(route = Screen.MainScreen.route) {
            CategoryScreen(CategoryViewModel(), navController = navController)
        }
        composable(route = Screen.DetailScreen.route) { backStackEntry ->
            val file = backStackEntry.arguments?.getString("data")
            val title = backStackEntry.arguments?.getString("title")
            if (file != null && title != null) {
                DetailScreen(DetailViewModel(), navController, file,title)
            } else {
                DetailScreen(DetailViewModel(), navController, "Hata","Hata")
            }
        }
        composable(route = Screen.ShareScreen.route) {backStackEntry ->
            val url = backStackEntry.arguments?.getString("image")
            val title = backStackEntry.arguments?.getString("title")

            if (url != null && title != null) {
                ShareScreen(ShareViewModel(), navController, title,url)
            } else {
                ShareScreen(ShareViewModel(), navController, "Hata","Hata")
            }

        }
    }
}