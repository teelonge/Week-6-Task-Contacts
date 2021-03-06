package com.example.week_6_task.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.week_6_task.R
import com.example.week_6_task.data.Contact

/**
 * Adapter class responsible for creating and binding views in
 * the contact list
 */
class ContactsAdapter : RecyclerView.Adapter<ContactsAdapter.ContactViewHolder>(){

    private var contacts = mutableListOf<Contact>()
    var listener : RecyclerViewClickListener? = null


    class ContactViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        var txtContactName : TextView = itemView.findViewById(R.id.txtContactName)
        var holderItem : ConstraintLayout = itemView.findViewById(R.id.holderItem)
        fun bind(contact: Contact){
            txtContactName.text = contact.name
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.contact_list_item,parent,false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        // Binds each contact according to their position
        holder.bind(contacts[position])

        // Handles the click listener of each item in the contact list
        holder.holderItem.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it,contacts[position])
        }

    }

    override fun getItemCount() = contacts.size

    // Sets the contacts declared here to the contacts received from the value event listener
    fun setContacts(contactsNow : List<Contact>){
        this.contacts = contactsNow as MutableList<Contact>
        notifyDataSetChanged()

    }

}