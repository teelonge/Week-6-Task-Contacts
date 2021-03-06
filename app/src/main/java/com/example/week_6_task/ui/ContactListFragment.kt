package com.example.week_6_task.ui


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.week_6_task.R
import com.example.week_6_task.data.Contact
import com.example.week_6_task.databinding.FragmentContactListBinding
import com.example.week_6_task.logic.Contacts


/**
 * This fragment retrieves the list of contacts in the firebase realtime database
 * and displays it in a recyclerView, from which you can navigate to the contact details
 * or create a new contact
 **/
class ContactListFragment : Fragment() , RecyclerViewClickListener{

    private val adapter = ContactsAdapter()
    private var _binding : FragmentContactListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentContactListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Navigates to the create new contact fragment
        binding.floatingActionButton.setOnClickListener {
          //  val action = ContactListFragmentDirections.actionContactListFragmentToAddContactFragment()
            goto(R.id.addContactFragment)
        }

        // Sets up the adapter to listen for click events and also retrieve and populate the contact list
        adapter.listener = this
        binding.recyclerContacts.adapter = adapter
        Contacts.retrieveContacts(adapter)


    }

    // When a contact is clicked from the contact list, navigates to the details fragment
    override fun onRecyclerViewItemClicked(view: View, contact: Contact) {
        when(view.id){
            R.id.holderItem->{
                    val action = ContactListFragmentDirections.actionContactListFragmentToContactDetailsFragment(contact)
                    findNavController().navigate(action)
                }
            }
        }

}


fun Fragment.goto(id : Int){
    findNavController().navigate(id)
}