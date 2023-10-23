package com.mobilesword.resimlisozler.detailscreen

import android.app.Activity
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.mobilesword.resimlisozler.data.repo.CategoriesRepo
import com.mobilesword.resimlisozler.data.repo.DetailRepo
import com.mobilesword.resimlisozler.models.Categories
import com.mobilesword.resimlisozler.models.Images
import kotlinx.coroutines.launch


class DetailViewModel : ViewModel() {
    private val repository = DetailRepo()
    var mInterstitialAd: InterstitialAd? = null
    private val _images = MutableLiveData<List<Images>>()
    val images: LiveData<List<Images>> = _images

    fun fetchImages(fileName:String) {
        viewModelScope.launch {
            try {
                val images = repository.getImages(fileName)
                _images.value = images
            } catch (e: Exception) {
                // Handle error
            }
        }
    }



    fun loadInterstitial(context: Context) {
        InterstitialAd.load(
            context,
            "ca-app-pub-4275218970636966/6112336106", //Change this with your own AdUnitID!
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdFailedToLoad(adError: LoadAdError) {
                    mInterstitialAd = null
                }

                override fun onAdLoaded(interstitialAd: InterstitialAd) {
                    mInterstitialAd = interstitialAd
                }
            }
        )
    }


    fun controlInterstitial(): Boolean {
        if (mInterstitialAd == null) {
            return false
        } else {
            return true
        }
    }
    fun showInterstitial(context: Context, onAdDismissed: () -> Unit) {
        val activity = context as? Activity

        if (mInterstitialAd != null && activity != null) {
            mInterstitialAd?.fullScreenContentCallback = object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    mInterstitialAd = null
                }

                override fun onAdDismissedFullScreenContent() {
                    mInterstitialAd = null

                    loadInterstitial(context)
                    onAdDismissed()
                }
            }
            mInterstitialAd?.show(activity)
        }
    }

    fun removeInterstitial() {
        mInterstitialAd?.fullScreenContentCallback = null
        mInterstitialAd = null
    }
}

