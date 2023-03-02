package tech.primis.androidsampleapplication.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import tech.primis.androidsampleapplication.databinding.ActivityMainBinding

private const val PRIMIS_URL = "https://www.primis.tech/"

/**
 * The MainActivity class is the main entry point of the application. It sets click listeners for
 * buttons that navigate to the different activities of the application or to the Primis webpage.
*/
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.goToRecyclerViewBtn.setOnClickListener {
            startActivity(Intent(this, RecyclerViewSampleActivity::class.java))
        }

        binding.goToScrollViewBtn.setOnClickListener {
            startActivity(Intent(this, ScrollViewSampleActivity::class.java))
        }

        binding.goToWebViewBtn.setOnClickListener {
            startActivity(Intent(this, WebViewSampleActivity::class.java))
        }

        // Set up a click listener for the logo, launching the Primis website when it is clicked
        binding.logoPrimis.setOnClickListener {

            val primisWebPage = Uri.parse(PRIMIS_URL)

            val intent = Intent(Intent.ACTION_VIEW, primisWebPage)

            // Check if there is an activity available to handle the Intent, and launch it if
            // there is
            intent.resolveActivity(packageManager)?.let {
                startActivity(intent)
            }
        }
    }
}