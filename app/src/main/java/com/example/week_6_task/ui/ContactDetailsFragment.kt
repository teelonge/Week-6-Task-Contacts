package com.example.week_6_task.ui

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.week_6_task.R
import com.example.week_6_task.data.Contact
import com.example.week_6_task.data.REQUEST_CALL
import com.example.week_6_task.databinding.FragmentContactDetailsBinding
import com.example.week_6_task.logic.Contacts
import java.util.*


/**
 * This fragment displays a contact details inclusive is the Name,
 * phoneNumber and Email.
 *
 * Also it has the option to call, edit , delete or share the contact
 */
class ContactDetailsFragment : Fragment() {

    private lateinit var contact : Contact
    private val args by navArgs<ContactDetailsFragmentArgs>()
    private var _binding : FragmentContactDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Receives the selected contact from the contactList and saves it in this contact object
        contact = args.contact
        _binding = FragmentContactDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        /*
         * Gets and set the name from the contact and splits it into firstName and LastName
         * Also sets the textField for the phoneNumber and Email address
         */
        if (args.contact.name!!.contains(" ")){
            binding.txtContactFirstName.text =getString(R.string.first_name,args.contact.name!!.split(" ")[0].capitalize(Locale.ROOT))
            binding.txtContactLastName.text = getString(R.string.last_name,args.contact.name!!.split(" ")[1].capitalize(Locale.ROOT))
        }else{
            binding.txtContactFirstName.text = getString(R.string.first_name,args.contact.name)
            binding.txtContactLastName.text = getString(R.string.last_name," ")
        }

        binding.contactPhoneNumber.text = getString(R.string.phone_number,args.contact.phone)
        binding.contactEmail.text = getString(R.string.email,args.contact.email)

        binding.btnDial.setOnClickListener {
            makePhoneCall()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.details_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /*
    Handles the logic of sharing contact, deleting a contact from the database
    and editing a contact
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.share ->{
                shareContact()
            }
            R.id.delete -> {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle("Are you sure you want to delete")
                    it.setPositiveButton("Yes") { _, _ ->
                        Contacts.deleteContact(contact)
                        val action =
                            ContactDetailsFragmentDirections.actionContactDetailsFragmentToContactListFragment()
                        findNavController().navigate(action)
                    }
                    it.setNegativeButton("Cancel") { dialog, _ ->
                        dialog.dismiss()
                    }
                }.create().show()
            }

            // Navigates to the editContact fragment to handle the contact edit
            R.id.editContact->{
                val action = ContactDetailsFragmentDirections.actionContactDetailsFragmentToEditContactFragment(contact)
                findNavController().navigate(action)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    // Generates an intent that contains the contacts details for sharing
    private fun shareContact() {

        val intent = Intent().apply {
            action = Intent.ACTION_SEND_MULTIPLE
            putExtra(Intent.EXTRA_TEXT, contact.phone)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(intent,"Share contact to...")
        startActivity(shareIntent)
    }

    /*
    Request permission to access the phone's dialer, if permission granted, then
    the call intent is processed and the contact gets dialed
     */
    private fun makePhoneCall() {
        val string = binding.contactPhoneNumber.text.toString().trim()
        if (string.isNotEmpty()){
            if (ContextCompat.checkSelfPermission(requireContext(),android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                requestPermissions(arrayOf(android.Manifest.permission.CALL_PHONE), REQUEST_CALL)
            }else{
                val number = "tel:$string"
                val intent = Intent(Intent.ACTION_CALL)
                intent.data = Uri.parse(number)
                startActivity(intent)
            }
        }
    }

    // Processes the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                makePhoneCall()
            }else{
                Toast.makeText(requireContext(), "Permission DENIED", Toast.LENGTH_SHORT).show()
            }
        }
    }

}