package tech.primis.androidsampleapplication.activities

import android.graphics.Color
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import tech.primis.androidsampleapplication.utils.Constants
import tech.primis.androidsampleapplication.databinding.ActivityWebViewSampleBinding
import tech.primis.player.PrimisPlayer
import tech.primis.player.PrimisScrollJSInterface

/*
 * This class sets up a WebView to display a webpage and initializes the PrimisPlayer SDK to display
 * the video player inside of it. The PrimisPlayer is created and initialized in the
 * onPageFinished() method of a custom WebViewClient that is set as the WebView's webViewClient.
 *
 * The PrimisPlayer instance is initialized with a list of configuration parameters, including the
 * placement ID, a reference to the WebView that will contain the player, and a
 * PrimisScrollJSInterface object that allows the player to identify changes in the web page content
 * height. The player is added to the WebView using the add() method.
 *
 * The WebView is loaded with a URL pointing to an HTML file that contains the div element with an
 * id of "primisPlayerSdkPlaceholder" that the PrimisPlayer SDK will use to locate the video on.
 *
 * When the activity is destroyed, the PrimisPlayer is removed using the remove() method, and the
 * PrimisPlayer instance is set to null.
 *
 * Read more here https://docs.primis.tech/docs/webview-implementation-android
 */

// In order to function properly, your website HTML must contain a div element with
// id="primisPlayerSdkPlaceholder"

private const val PRIMIS_WEBVIEW_EXAMPLE_URL = "file:///android_asset/primis_webview_example.html"

class WebViewSampleActivity : SampleBaseActivity() {

    private var primisPlayer: PrimisPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityWebViewSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "WebView Sample"

        // Creating an interface to allow PrimisPlayer to identify changes in the web page content
        // height
        val primisScrollJSInterface = PrimisScrollJSInterface()

        val webViewClient =
            object : WebViewClient() {
                // Here we'll add the Primis player when the web page is done loading
                override fun onPageFinished(view: WebView?, url: String?) {
                    super.onPageFinished(view, url)

                    view?.let { webView ->

                        // Create and initialize the PrimisPlayer instance.

                        // The apply function is then called on the PrimisPlayer object, which
                        // allows the setConfig method to be called with a list of configuration
                        // parameters.

                        // The also function then used to add the player to the view

                        primisPlayer =
                            PrimisPlayer(this@WebViewSampleActivity)
                                .apply {

                                    val config = listOf(

                                        // Placement id
                                        PrimisPlayer.param(
                                            "placementId", Constants.PLACEMENT_ID
                                        ),

                                        // The app's Webview
                                        PrimisPlayer.param("isInWebViewApp", webView),

                                        // The Scroll JS interface
                                        PrimisPlayer.param(
                                            "hostAppWebViewJSScrollInterface",
                                            primisScrollJSInterface
                                        )
                                    )

                                    // Setting the player configuration list
                                    setConfig(config)
                                }
                                .also {

                                    // Calling the player's add() method
                                    it.add()
                                }

                    }
                }
            }

        binding.webView.webViewClient = webViewClient

        // Adding the ScrollJSInterface to the WebView as well
        binding.webView.addJavascriptInterface(
            primisScrollJSInterface,
            PrimisScrollJSInterface.Companion.PRIMIS_SCROLL_JS_INTERFACE_OBJ
        )

        // Enabling JS so Primis SDK could increase the div to fit its size
        binding.webView.settings.javaScriptEnabled = true

        binding.webView.setBackgroundColor(Color.TRANSPARENT)

        binding.webView.loadUrl(PRIMIS_WEBVIEW_EXAMPLE_URL)
    }

    override fun onDestroy() {
        super.onDestroy()

        primisPlayer?.remove()

        primisPlayer = null
    }
}
