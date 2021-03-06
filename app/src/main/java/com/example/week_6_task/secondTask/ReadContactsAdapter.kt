package com.example.week_6_task.secondTask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.week_6_task.R
import com.example.week_6_task.data.Contact

/**
 * This adapter gets the readContacts from the activity after permission must have been granted
 * and the contacts have been read, then it populates the recycler view in the activity with
 * the name and phone number of each contact present in the phone it is installed
 */
class ReadContactsAdapter(private val readContacts: ArrayList<Contact>) : RecyclerView.Adapter<ReadContactsAdapter.ReadContactViewHolder>(){



    class ReadContactViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        // contact name and for each item and gets binded in the onBindViewHolder
        var readContactName : TextView = itemView.findViewById(R.id.readContactName)
        var readContactPhone : TextView = itemView.findViewById(R.id.readContactPhone)

        fun bind(readContact: Contact){
            readContactName.text = readContact.name
            readContactPhone.text =readContact.phone
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.read_contact_item,parent,false)
        return ReadContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReadContactViewHolder, position: Int) {
        holder.bind(readContacts[position])

    }

    override fun getItemCount() = readContacts.size


}