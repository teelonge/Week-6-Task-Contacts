package com.example.week_6_task.logic


import com.example.week_6_task.data.CONTACTS_PARENT_NODE
import com.example.week_6_task.data.Contact
import com.example.week_6_task.ui.ContactsAdapter
import com.google.firebase.database.*

/**
 * A singleton class that handles all the logic of adding, editing, deleting
 * and retrieving contacts to and from the firebase realtime database
 */
object Contacts {

    // Creates an instance and reference to the database via a single unique parent node
    private val fbContacts = FirebaseDatabase.getInstance().getReference(CONTACTS_PARENT_NODE)

    // This holds the contacts that will be retrieved from the firebase database
    val contacts = mutableListOf<Contact>()
    val contact = Contact()

    // Handles adding a new contact to the firebase by creating a unique ID before setting its value
    fun addContact(contact: Contact){
        contact.id = fbContacts.push().key
        fbContacts.child(contact.id!!).setValue(contact)
    }

    // Retrieves all contacts from the database using the snapshot of each data
    fun retrieveContacts(adapter: ContactsAdapter){
        fbContacts.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    contacts.clear()
                    for (contactSnapshot in snapshot.children){
                        val contact = contactSnapshot.getValue(Contact::class.java)
                        contact?.id = contactSnapshot.key
                        contact?.let { contacts.add(it) }
                    }
                    adapter.setContacts(contacts)
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    // Handles editing and update a contact already present in the database
    fun updateContact(contact: Contact){
        fbContacts.child(contact.id!!).setValue(contact)
    }

    // Handles deleting a particular contact from the database using its unique ID
    fun deleteContact(contact: Contact){
        fbContacts.child(contact.id!!).setValue(null)
    }


}

