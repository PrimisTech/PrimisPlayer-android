package tech.primis.androidsampleapplication.activities

import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import tech.primis.androidsampleapplication.databinding.ActivityRecyclerViewSampleBinding
import tech.primis.androidsampleapplication.adapters.RecyclerViewSampleAdapter

/*
 *  This class sets up the necessary components for a RecyclerView, and demonstrates how to use it
 *  to display a list of items on the screen using the RecyclerViewSampleAdapter.
 *
 *  Read more here: https://docs.primis.tech/docs/recyclerview-implementation-android
 */
class RecyclerViewSampleActivity : SampleBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityRecyclerViewSampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "RecyclerView Sample"

        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        // Add a vertical divider between each item in the RecyclerView
        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        )

        binding.recyclerView.adapter = RecyclerViewSampleAdapter()
    }
}