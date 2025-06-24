package com.example.marvelcharacterapp.View

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun WebViewScreen(modifier : Modifier,url: String) {
    AndroidView(
        factory = { context ->
            WebView(context).apply {
                // Set up WebView settings
                settings.javaScriptEnabled = true
                webViewClient = WebViewClient()  // Optional, to open links within the WebView
                loadUrl(url)
            }
        },
        modifier = Modifier.fillMaxSize() // Fill the available space with the WebView
    )
}