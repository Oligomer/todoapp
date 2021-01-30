package com.dpug.todoapp.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.dpug.todoapp.R
import com.dpug.todoapp.data.models.ToDoData
import com.dpug.todoapp.data.viewmodel.ToDoViewModel
import com.dpug.todoapp.databinding.FragmentAddBinding
import com.dpug.todoapp.fragments.SharedViewModel

class AddFragment : Fragment() {

    private val mToDoViewModel: ToDoViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var binding: FragmentAddBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentAddBinding.inflate(inflater, container, false)

        // Set Menu
        setHasOptionsMenu(true)

        binding?.spinnerPriorities?.onItemSelectedListener = mSharedViewModel.listener

        return binding!!.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_add) {
            insertDataToDb()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDb() {

        val title = binding?.etTitle?.text.toString()
        val description = binding?.etDescription?.text.toString()
        val priority = binding?.spinnerPriorities?.selectedItem.toString()

        val validation = mSharedViewModel.verifyDataFromUser(title, description)
        if(validation)  {
            // Insert Data to DataBase
            val newData = ToDoData(
                0,
                title,
                mSharedViewModel.parsePriority(priority),
                description
            )
            mToDoViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Добавлено!", Toast.LENGTH_SHORT).show()
            //Navigate Back
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(), "Заполните все поля!", Toast.LENGTH_SHORT).show()
        }
    }
}