package com.example.week_6_task.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.week_6_task.R
import com.example.week_6_task.data.Contact
import com.example.week_6_task.databinding.FragmentAddContactBinding
import com.example.week_6_task.logic.Contacts
import com.example.week_6_task.logic.ValidateContact

/**
 * This fragment allows you to add a new contact
 * to the list of contact in the firebase database
 */
class EditContactFragment() : Fragment() {


    private var _binding: FragmentAddContactBinding? = null
    private val binding get() = _binding!!
    private lateinit var contact: Contact
    private val args by navArgs<EditContactFragmentArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Receives the contact clicked and passed from the ContactListFragment
        contact = args.contact

        _binding = FragmentAddContactBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Populates the respective editText fields with incoming contact object
        binding.editTextTextPersonName.setText(contact.name)
        binding.editTextPhone.setText(contact.phone)
        binding.editTextTextEmailAddress.setText(contact.email)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.create_contact_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    /**
     * Creates a new contact in the firebase database when the checl
     * item is selected
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.createContact -> {
                updateContact()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateContact() {
        val name = binding.editTextTextPersonName.text.toString().trim()
        val phoneNumber = binding.editTextPhone.text.toString().trim()
        val email = binding.editTextTextEmailAddress.text.toString().trim()

        /*
         Gets the name, phoneNumber and email input, validates the input to make sure
         it is not empty and also makes some necessary checks, if returns true,
         then updates the contact object in the firebase realtime
         database
       */
        if (ValidateContact.validateContactInput(name, phoneNumber, email)) {
            contact.name = name
            contact.phone = phoneNumber
            contact.email = email

            Contacts.updateContact(contact)
            val action =
                EditContactFragmentDirections.actionEditContactFragmentToContactListFragment()
            findNavController().navigate(action)
        } else {
            Toast.makeText(context, "Enter all fields correctly", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}