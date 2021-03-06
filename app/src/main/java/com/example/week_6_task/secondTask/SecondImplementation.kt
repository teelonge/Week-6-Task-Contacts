package com.example.week_6_task.secondTask

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.week_6_task.data.Contact
import com.example.week_6_task.data.REQUEST_READ_CONTACT
import com.example.week_6_task.databinding.ActivitySecondImplementationBinding

class SecondImplementation : AppCompatActivity() {


    private lateinit var binding : ActivitySecondImplementationBinding
    // Creates an arrayList that will hold all the read contacts after permission is granted
    private val readContacts = ArrayList<Contact>()
    private lateinit var readContactsAdapter: ReadContactsAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondImplementationBinding.inflate(layoutInflater)

        val view = binding.root
        setContentView(view)

        /*
        Request permission from user to read contacts, if user
        denies a button appears so that the user can request again
         */
       requestPermissionToReadContacts()

        readContactsAdapter = ReadContactsAdapter(readContacts)

        binding.readContactRecycler.layoutManager = LinearLayoutManager(this)
        binding.readContactRecycler.adapter = readContactsAdapter

        // Requests permission again if permission is denied
        binding.button.setOnClickListener {
            binding.button.visibility = View.GONE
            requestPermissionToReadContacts()
        }
    }

    // Handles the permission request result
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_READ_CONTACT){
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){

                // Read contacts and binds the view if permission is granted
                readContacts()
                readContactsAdapter = ReadContactsAdapter(readContacts)
                binding.readContactRecycler.layoutManager = LinearLayoutManager(this)
                binding.readContactRecycler.adapter = readContactsAdapter

            }else{
              binding.button.visibility = View.VISIBLE
               Toast.makeText(this, "Permission DENIED", Toast.LENGTH_SHORT).show()

            }
        }
    }

    // This method is responsible for requesting the user's permission to read the phone contacts
    private fun requestPermissionToReadContacts(){
        if (ContextCompat.checkSelfPermission(this,Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED)
        {
            requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_READ_CONTACT)
        }
        else{
            readContacts()
        }
    }

    /*
   This method does the actual reading of the phone contacts
   after permission must have been granted using the contentResolver and ContactsContract
     */
    private fun readContacts() {
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null)

        while (cursor?.moveToNext() == true)
        {
            val name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val mobile =cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            val contact = Contact(name = name,phone = mobile)
            readContacts.add(contact)
        }
    }
}