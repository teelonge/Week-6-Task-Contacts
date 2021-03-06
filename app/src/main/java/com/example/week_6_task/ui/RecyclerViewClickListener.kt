package com.example.week_6_task.ui

import android.view.View
import com.example.week_6_task.data.Contact

/**
 * Interface to handle clicking of each item in the contact
 * list.
 */
interface RecyclerViewClickListener {
    fun onRecyclerViewItemClicked(view : View, contact : Contact)
}