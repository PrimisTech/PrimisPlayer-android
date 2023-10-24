package tech.primis.androidsampleapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import tech.primis.androidsampleapplication.databinding.ItemMockBinding
import tech.primis.androidsampleapplication.databinding.ItemPlayerBinding
import tech.primis.androidsampleapplication.utils.Constants
import tech.primis.player.PrimisPlayer
import tech.primis.player.configuration.PrimisConfiguration

/*
 * This is an example class for a RecyclerView adapter that shows the Primis player and interacts
 * with Primis SDK
 *
 * Read more here: https://docs.primis.tech/docs/recyclerview-implementation-android
 */

// Top-level private constants

/**
 * Indicate that the view type is a mock. It is used to build the RecyclerView mock items.
 */
private const val VIEW_TYPE_MOCK = 1

/**
 * Indicate that the view type is for the Primis player. Use this to return a view that holds the
 * player.
 */
private const val VIEW_TYPE_PLAYER = 2

/** The RecyclerViewSampleAdapter class is responsible for creating and binding views for each item
 * in the RecyclerView.
 *
 * In this specific implementation, the RecyclerView contains a [PrimisPlayer] instance which is
 * created and initialized in the [onAttachedToRecyclerView] method, and removed in the
 * [onDetachedFromRecyclerView] method.
 *
 * The adapter overrides the [getItemViewType] method to return the appropriate view type based
 * on the position of the item: [VIEW_TYPE_PLAYER] is used for the item that contains the
 * [PrimisPlayer] instance, and [VIEW_TYPE_MOCK] is used for the other items.
 *
 * The [onCreateViewHolder] method returns a new [ViewHolder] instance with the appropriate
 * view binding based on the view type.
 *
 * The [ViewHolder] inner class is responsible for holding references to the views of each
 * item in the RecyclerView. The [ViewHolder.playerContainer] field in this class is used to
 * hold the view that will contain the [PrimisPlayer] video player.
 *
 * This class uses [onViewAttachedToWindow] and [onViewDetachedFromWindow] methods to notify the
 * [PrimisPlayer] when its view holder is attached or detached from the viewable area of the
 * RecyclerView, respectively.
 *
 */
class RecyclerViewSampleAdapter :
    RecyclerView.Adapter<RecyclerViewSampleAdapter.ViewHolder>() {

    // The Primis player instance
    private var primisPlayer: PrimisPlayer? = null

    // The position of the player in the RecyclerView
    private val playerPosition = 1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        // Inflate the appropriate binding class based on the view type. If viewType is
        // VIEW_TYPE_PLAYER, inflate the ItemPlayerBinding. Otherwise, inflate the ItemMockBinding.
        val binding =
            when (viewType) {
                VIEW_TYPE_PLAYER -> {
                    ItemPlayerBinding.inflate(inflater, parent, false)
                }
                else -> {
                    ItemMockBinding.inflate(inflater, parent, false)
                }
            }

        return ViewHolder(binding)
    }

    // Binds data to the view - not being used in this adapter
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {}

    // Called when a view is detached from the RecyclerView
    override fun onViewDetachedFromWindow(holder: ViewHolder) {
        super.onViewDetachedFromWindow(holder)

        if (holder.itemViewType == VIEW_TYPE_PLAYER) {
            // The player's view had exit the viewable area

            primisPlayer?.onDetachedFromRecyclerView()
        }
    }

    // Called when a view is attached to the RecyclerView
    override fun onViewAttachedToWindow(holder: ViewHolder) {
        super.onViewAttachedToWindow(holder)

        if (holder.itemViewType == VIEW_TYPE_PLAYER) {
            // The player's view had entered the viewable area

            holder.playerContainer?.let { playerContainer ->

                // Passing the player container to PrimisPlayer
                primisPlayer?.onAttachedToRecyclerView(playerContainer)
            }
        }
    }

    // Called when the adapter is attached to the RecyclerView
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)

        // Create and initialize the PrimisPlayer instance.

        // The also function then used to add the player to the RecyclerView

        primisPlayer = PrimisConfiguration.Builder()
            .placement(Constants.PLACEMENT_ID)
            .recyclerView(recyclerView)
            .createPlayer(recyclerView.context)
            .also {

                // Add the player to the RecyclerView
                it.add()
            }
    }

    // Called when the adapter is detached from the RecyclerView
    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)

        // Remove the player from the RecyclerView
        primisPlayer?.remove()

        primisPlayer = null
    }

    override fun getItemCount(): Int {
        return 30
    }

    class ViewHolder(binding: ViewBinding) : RecyclerView.ViewHolder(binding.root) {

        // The container for the Primis player view
        var playerContainer: FrameLayout? = null

        init {

            // Get the player container from the view binding only if it is the type of
            // ItemPlayerBinding
            playerContainer = (binding as? ItemPlayerBinding)?.playerContainer
        }
    }

    /**
     * Returns the type of view that should be used for the item at the specified position.
     *
     * @param position The position of the item whose view type is to be returned.
     * @return The type of view to be used for the item at the specified position. If the position
     * is equal to the player position, it returns [VIEW_TYPE_PLAYER]; otherwise, it returns
     * [VIEW_TYPE_MOCK].
     */
    override fun getItemViewType(position: Int): Int {
        return if (position == playerPosition) {
            VIEW_TYPE_PLAYER
        } else {
            VIEW_TYPE_MOCK
        }
    }
}