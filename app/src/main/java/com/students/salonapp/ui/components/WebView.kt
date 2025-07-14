import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.Modifier

@Composable
fun MapWebView(
    modifier: Modifier = Modifier,
    url: String = "https://www.openstreetmap.org/?#map=16/56.63233/47.86282" // пример OpenStreetMap с центром на координатах
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                webViewClient = WebViewClient() // чтобы ссылки открывались в WebView
                settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.cacheMode = WebSettings.LOAD_DEFAULT
                loadUrl(url)
            }
        },
        update = { webView ->
            webView.settings.javaScriptEnabled = true
            webView.loadUrl(url)
        }
    )
}
