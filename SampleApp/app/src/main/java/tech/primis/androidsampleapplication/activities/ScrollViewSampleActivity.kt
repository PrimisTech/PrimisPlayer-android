package tech.primis.androidsampleapplication.activities

import android.os.Bundle
import tech.primis.androidsampleapplication.utils.Constants
import tech.primis.androidsampleapplication.databinding.ActivityScrollViewSampleBinding
import tech.primis.player.PrimisPlayer

/*
 * This activity class demonstrates how to implement the [PrimisPlayer] Android SDK inside a
 * ScrollView.
 * The activity initializes the [PrimisPlayer] instance with a list of configuration parameters.
 * These parameters include the placement ID and the player container, which is a FrameLayout that
 * contains the player.
 *
 * Once the configuration parameters are set, the player is added to the layout using the
 * [PrimisPlayer.add] method.
 *
 * Read more here: https://docs.primis.tech/docs/scrollview-implementation-android
 */

class ScrollViewSampleActivity : SampleBaseActivity() {

    private var primisPlayer: PrimisPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityScrollViewSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "ScrollView Sample"

        // Create and initialize the PrimisPlayer instance.

        // The apply function is then called on the PrimisPlayer object, which allows the setConfig
        // method to be called with a list of configuration parameters.

        // The also function then used to add the player to the view
        primisPlayer =
            PrimisPlayer(this)
                .apply {

                    val config = listOf(
                        // Placement id
                        PrimisPlayer.param("placementId", Constants.PLACEMENT_ID),

                        // The player container - a FrameLayout to contain the player
                        PrimisPlayer.param("primisPlayerContainer", binding.playerContainer)
                    )

                    // Set the player configuration list
                    setConfig(config)
                }
                .also {

                    // Add the player to the layout
                    it.add()
                }
    }

    override fun onDestroy() {
        super.onDestroy()

        primisPlayer?.remove()

        primisPlayer = null

    }
}
