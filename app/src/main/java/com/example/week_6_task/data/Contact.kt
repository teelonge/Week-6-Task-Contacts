package com.example.week_6_task.data

import android.os.Parcelable
import androidx.versionedparcelable.VersionedParcelize
import com.google.firebase.database.Exclude
import kotlinx.parcelize.Parcelize

/**
 * This is the contact object that represent a contact in list, parcelized
 * in order for it to passed across fragment via safe args when it is needed
 */
@Parcelize
data class Contact(
    @get:Exclude
    var id : String? = null,var name : String? = null,
    var phone : String? = null, var email : String? = null,
) : Parcelable

