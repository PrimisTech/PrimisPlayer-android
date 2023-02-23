package tech.primis.androidsampleapplication.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

/**
 * This class serves as a base class for all sample activities in the application. It provides
 * common functionality that is shared across all sample activities.
 */
open class SampleBaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Showing the home button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }


    // this event will enable the back function to the button on press
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}